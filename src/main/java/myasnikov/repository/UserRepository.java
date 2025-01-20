package myasnikov.repository;

import myasnikov.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository implements Repository<User> {
  private final Map<Long, User> users = new HashMap<>();
  private long nextId = 1;

  @Override
  public void save(User user) {
    if (user.getId() == null) {
      user.setId(nextId++);
    }
    users.put(user.getId(), user);
  }

  @Override
  public Optional<User> findById(Long id) {
    return Optional.ofNullable(users.get(id));
  }

  @Override
  public Map<Long, User> findAll() {
    return new HashMap<>(users);
  }

  @Override
  public void deleteById(Long id) {
    users.remove(id);
  }

  public Optional<User> findByUsername(String username) {
    return users.values().stream().filter(user -> user.getUsername().equals(username)).findFirst();
  }
}
