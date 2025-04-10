package myasnikov.dao;


import myasnikov.entity.User;
import org.hibernate.SessionFactory;

public class UserDao extends GenericDao<User>{
    public UserDao(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
    }
}
