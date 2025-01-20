package myasnikov.repository;

import myasnikov.entity.Entity;

import java.util.Optional;
import java.util.Map;

public interface Repository<T extends Entity> {
  void save(T entity);

  Optional<T> findById(Long id);

  Map<Long, T> findAll();

  void deleteById(Long id);
}
