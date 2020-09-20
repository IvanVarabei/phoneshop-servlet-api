package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.exception.ItemNotFoundException;

import java.util.List;

public interface GenericDao<T> {
    void save(T storageItem);

    T find(Long id) throws ItemNotFoundException;

    List<T> findAll();

    void delete(long id);
}
