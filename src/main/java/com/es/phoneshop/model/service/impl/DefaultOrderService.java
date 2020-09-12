package com.es.phoneshop.model.service.impl;

import com.es.phoneshop.model.dao.OrderDao;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.entity.PaymentMethod;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.service.OrderService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {
    private static final int DELIVERY_COST = 11;
    private OrderDao orderDao = ArrayListOrderDao.getInstance();
    private ProductDao productDao = ArrayListProductDao.getInstance();

    private DefaultOrderService() {
    }

    private static class OrderServiceHolder {
        private static final DefaultOrderService DEFAULT_ORDER_SERVICE_INSTANCE = new DefaultOrderService();
    }

    public static DefaultOrderService getInstance() {
        return OrderServiceHolder.DEFAULT_ORDER_SERVICE_INSTANCE;
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setCartItemList(cart.getCartItemList().stream().map(cartItem -> {
            try {
                return cartItem.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList()));
        order.setSubtotal(cart.getTotalCost());
        order.setDeliveryCost(calculateDeliveryCost());
        order.setTotalCost(order.getSubtotal().add(order.getDeliveryCost()));
        return order;
    }

    @Override
    public void placeOrder(Order order) {
        order.setSecureId(UUID.randomUUID().toString());
        orderDao.save(order);
        order.getCartItemList().forEach(cartItem -> productDao.updateProductStock(cartItem.getProduct(),
                cartItem.getProduct().getStock() - cartItem.getQuantity()));
    }

    @Override
    public Order findOrder(Long id) throws ItemNotFoundException {
        return orderDao.find(id);
    }

    @Override
    public Order findOrderBySecureId(String id) throws ItemNotFoundException {
        return orderDao.findOrderBySecureId(id);
    }

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    private BigDecimal calculateDeliveryCost() {
        return new BigDecimal(DELIVERY_COST);
    }
}
