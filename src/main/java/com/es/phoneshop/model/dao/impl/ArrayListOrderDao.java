package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.OrderDao;
import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.exception.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ArrayListOrderDao implements OrderDao {
    private List<Order> orderList = new ArrayList<>();

    private ArrayListOrderDao(){}

    private static class ArrayListOrderDaoHolder{
        private static final ArrayListOrderDao ARRAY_LIST_ORDER_DAO_INSTANCE = new ArrayListOrderDao();
    }

    public static ArrayListOrderDao getInstance(){
        return ArrayListOrderDaoHolder.ARRAY_LIST_ORDER_DAO_INSTANCE;
    }

//    @Override
//    public synchronized Order getOrder(Long id) throws OrderNotFoundException {
//        return orderList.stream().filter(o -> o.getId().equals(id)).findAny()
//                .orElseThrow(OrderNotFoundException::new);
//    }

    @Override
    public synchronized void save(Order order) {
        Long id = order.getId();
        if(id != null){
            orderList.remove(order);
        }else{
            order.setId(333333l);
        }
        orderList.add(order);
    }
}
