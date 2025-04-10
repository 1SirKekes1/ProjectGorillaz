package myasnikov.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class GenericDao<T> {
    private final Class<T> entityClass;

    private final SessionFactory sessionFactory;

    public GenericDao(final Class<T> clazzToSet, SessionFactory sessionFactory) {
        this.entityClass = clazzToSet;
        this.sessionFactory = sessionFactory;
    }

    public T getById(final long id) {
        return getCurrentSession().get(entityClass, id);
    }

    public List<T> getItems(int offset, int count) {
        Query<T> query = getCurrentSession().createQuery("from " + entityClass.getName(), entityClass);
        query.setFirstResult(offset);
        query.setMaxResults(count);
        return query.getResultList();
    }

    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + entityClass.getName(), entityClass).getResultList();
    }

    public void save(final T entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    public T update(final T entity) {
        return (T) getCurrentSession().merge(entity);
    }

    public void delete(final T entity) {
        getCurrentSession().delete(entity);
    }

    public void deleteById(final int entityId) {
        final T entity = getById(entityId);
        if (entity != null) {
            delete(entity);
        }
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
