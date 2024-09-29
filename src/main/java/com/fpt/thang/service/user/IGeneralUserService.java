package com.fpt.thang.service.user;

import java.util.Optional;

public interface IGeneralUserService<T> {
    Iterable<T> findAll();

    Optional<T> findById(Long id);

    T save(T t);

    void remove(Long id);

    boolean existsByUsername(String username);
}
