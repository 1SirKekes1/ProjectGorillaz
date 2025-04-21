package myasnikov.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class GenericDao<T> {
    private final Class<T> entityClass;

    private final SessionFactory sessionFactory;

    public GenericDao(final Class<T> classToSet, SessionFactory sessionFactory) {
        this.entityClass = classToSet;
        this.sessionFactory = sessionFactory;
    }

    public T getById(final long id) {
        Transaction transaction = null;
        Session session = getCurrentSession();
        try {
            transaction = session.beginTransaction();
            return getCurrentSession().get(entityClass, id);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to get an entity by id", e);
        }
    }

    public T getByStringAttribute(String attributeName, String value) {
        Transaction transaction = null;
        Session session = getCurrentSession();

        try {
            transaction = session.beginTransaction();
            String hql = "FROM " + entityClass.getSimpleName() + " WHERE " + attributeName + " = :value";
            return getCurrentSession().createQuery(hql, entityClass).setParameter("value", value).uniqueResult();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to get attribute", e);
        }
    }


    public List<T> findAll() {
        Transaction transaction = null;
        Session session = getCurrentSession();

        try {
            transaction = session.beginTransaction();
            String query = "from " + entityClass.getName();
            return sessionFactory.getCurrentSession().createQuery(query, entityClass).list();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to find all entities", e);
        }
    }

    public void save(final T entity) {
        Transaction transaction = null;
        Session session = getCurrentSession();

        try {
            transaction = session.beginTransaction();
            getCurrentSession().saveOrUpdate(entity);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save entity", e);
        }
    }

    public void update(final T entity) {
        Transaction transaction = null;
        Session session = getCurrentSession();

        try {
            transaction = session.beginTransaction();
            getCurrentSession().merge(entity);

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update entity", e);
        }

    }

    public void delete(final T entity) {
        Transaction transaction = null;
        Session session = getCurrentSession();

        try {
            transaction = session.beginTransaction();
            getCurrentSession().remove(entity);

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to find all entities", e);
        }
    }

    public void deleteById(final Long entityId) {
        Transaction transaction = null;
        Session session = getCurrentSession();

        try {
            transaction = session.beginTransaction();
            final T entity = getById(entityId);
            if (entity != null) {
                delete(entity);
            }

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete an entity", e);
        }
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
