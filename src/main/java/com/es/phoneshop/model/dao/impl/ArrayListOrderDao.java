package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.OrderDao;
import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.exception.ItemNotFoundException;

public class ArrayListOrderDao extends ArrayListGenericDao<Order> implements OrderDao {
    private ArrayListOrderDao() {
    }

    private static class ArrayListOrderDaoHolder {
        private static final ArrayListOrderDao ARRAY_LIST_ORDER_DAO_INSTANCE = new ArrayListOrderDao();
    }

    public static ArrayListOrderDao getInstance() {
        return ArrayListOrderDaoHolder.ARRAY_LIST_ORDER_DAO_INSTANCE;
    }

    @Override
    public synchronized Order findOrderBySecureId(String id) throws ItemNotFoundException {
        return items.stream().filter(o -> o.getSecureId().equals(id)).findAny()
                .orElseThrow(ItemNotFoundException::new);
    }
}
