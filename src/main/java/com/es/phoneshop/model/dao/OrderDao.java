package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.exception.ItemNotFoundException;

public interface OrderDao extends GenericDao<Order> {
    Order findOrderBySecureId(String id) throws ItemNotFoundException;
}
