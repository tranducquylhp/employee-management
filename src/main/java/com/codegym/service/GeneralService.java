package com.codegym.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GeneralService<E> {
    Page<E> findAll(Pageable pageable);
    void delete(long id);
    E findById(long id);
    Iterable<E> findAll();
}
