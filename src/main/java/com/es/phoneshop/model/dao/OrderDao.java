package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.exception.OrderNotFoundException;

public interface OrderDao {
    Order findOrder(Long id) throws OrderNotFoundException;

    Order findOrderBySecureId(String id) throws OrderNotFoundException;

    void save(Order order);
}
