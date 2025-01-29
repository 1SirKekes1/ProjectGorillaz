package myasnikov.service;

import myasnikov.config.AppConfig;
import myasnikov.entity.User;
import myasnikov.repository.Repository;
import myasnikov.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

public class UserService implements Service<User> {
  private final Repository<User> userRepository = AppConfig.getUserRepository();

  @Override
  public void save(User user) {
    if (user.getUsername() == null || user.getUsername().isEmpty()) {
      throw new IllegalArgumentException("Username cannot be empty");
    }
    userRepository.save(user);
  }

  @Override
  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public Map<Long, User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public void deleteById(Long id) {
    userRepository.deleteById(id);
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
