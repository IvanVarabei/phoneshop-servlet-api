package com.es.phoneshop.model.service;

import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.OutOfStockException;

import javax.servlet.http.HttpSession;

public interface CartService {
    void add(HttpSession session, Product product, int quantity) throws OutOfStockException;

    void update(HttpSession session, Product product, int quantity) throws OutOfStockException;

    void delete(HttpSession session, Product product);

    void clearCart(Cart cart);

    Cart extractCartOrSetNewOne(HttpSession session);
}
