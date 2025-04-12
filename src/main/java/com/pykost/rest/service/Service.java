package com.pykost.rest.service;

import java.util.List;
import java.util.Optional;

public interface Service<E, K> {
    E create(E e);

    Optional<E> getById(K k);

    void delete(K k);

    E update(K k, E e);

    List<E> getAll();

}
