package myasnikov.service;


import java.util.List;
import java.util.Optional;

public interface Service<T> {
    void save(T entity);

    Optional<T> findById(Long id);

    List<T> findAll();

    void deleteById(Long id);
}
