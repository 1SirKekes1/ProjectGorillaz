package myasnikov.service;

import myasnikov.config.AppConfig;
import myasnikov.config.HibernateConfig;
import myasnikov.dao.UserDao;
import myasnikov.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

import java.util.Optional;

public class UserService implements Service<User> {
    UserDao userDao = AppConfig.getUserDao();

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userDao.getById(id));
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    public void incrementAttribute(Long userId, String attribute) {
        Optional<User> userOptional = findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            switch (attribute.toLowerCase()) {
                case "games":
                    user.setGames(user.getGames() + 1);
                    break;
                case "wins":
                    user.setWins(user.getWins() + 1);
                    break;
                case "losses":
                    user.setLosses(user.getLosses() + 1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid attribute: " + attribute);
            }
            userDao.update(user);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

    public void createUser(String username, String email, String password,
                           Long games, Long wins, Long losses) {
        Transaction transaction = null;
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();

        try {
            transaction = session.beginTransaction();

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setGames(games);
            user.setWins(wins);
            user.setLosses(losses);

            userDao.save(user);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create user", e);
        }
    }
}
