package com.riwi.intro.repository;

import java.util.List;

public interface GenericRepository<T> {
    List<T> findAll();

    T findById(int id);

    T save(T entity);

    T update(int id, T entity);

    T deleteById(int id);
}
