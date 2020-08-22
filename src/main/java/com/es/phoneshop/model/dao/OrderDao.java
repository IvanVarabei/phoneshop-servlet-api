package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.exception.ItemNotFoundException;

public interface OrderDao {
    Order find(Long id) throws ItemNotFoundException;

    Order findOrderBySecureId(String id) throws ItemNotFoundException;

    void save(Order order);
}
