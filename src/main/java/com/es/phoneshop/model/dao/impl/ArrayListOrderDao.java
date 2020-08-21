package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.ArrayListGeneralDao;
import com.es.phoneshop.model.dao.OrderDao;
import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.exception.OrderNotFoundException;

public class ArrayListOrderDao extends ArrayListGeneralDao<Order> implements OrderDao {
    private ArrayListOrderDao() {
    }

    private static class ArrayListOrderDaoHolder {
        private static final ArrayListOrderDao ARRAY_LIST_ORDER_DAO_INSTANCE = new ArrayListOrderDao();
    }

    public static ArrayListOrderDao getInstance() {
        return ArrayListOrderDaoHolder.ARRAY_LIST_ORDER_DAO_INSTANCE;
    }

    @Override
    public synchronized Order findOrder(Long id) throws OrderNotFoundException {
        return items.stream().filter(o -> o.getId().equals(id)).findAny()
                .orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public synchronized Order findOrderBySecureId(String id) throws OrderNotFoundException {
        return items.stream().filter(o -> o.getSecureId().equals(id)).findAny()
                .orElseThrow(OrderNotFoundException::new);
    }
}
