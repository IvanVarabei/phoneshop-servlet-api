package com.es.phoneshop.model.service;

import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.entity.PaymentMethod;
import com.es.phoneshop.model.exception.ItemNotFoundException;

import java.util.List;

public interface OrderService {
    Order createOrder(Cart cart);

    void placeOrder(Order order);

    Order findOrderBySecureId(String id) throws ItemNotFoundException;

    List<PaymentMethod> getPaymentMethods();
}
