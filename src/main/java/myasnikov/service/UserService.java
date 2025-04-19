package myasnikov.service;

import myasnikov.config.AppConfig;
import myasnikov.dao.UserDao;
import myasnikov.entity.User;


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
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }
}
