package myasnikov.service;

import myasnikov.entity.Entity;

import java.util.Map;
import java.util.Optional;

public interface Service<T extends Entity> {
  void save(T entity);

  Optional<T> findById(Long id);

  Map<Long, T> findAll();

  void deleteById(Long id);
}
