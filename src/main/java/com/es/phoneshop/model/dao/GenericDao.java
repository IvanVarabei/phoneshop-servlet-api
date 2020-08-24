package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.exception.ItemNotFoundException;

public interface GenericDao <T>{
    void save(T storageItem);
    T find(Long id) throws ItemNotFoundException;
}
