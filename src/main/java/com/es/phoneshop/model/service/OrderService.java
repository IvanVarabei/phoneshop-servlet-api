package com.es.phoneshop.model.service;

import com.es.phoneshop.model.dao.OrderDao;
import com.es.phoneshop.model.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.CartItem;
import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.entity.PaymentMethod;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderService {
    private OrderDao orderDao = ArrayListOrderDao.getInstance();

    private OrderService(){
    }

    private static class OrderServiceHolder{
        private static final OrderService ORDER_SERVICE_HOLDER = new OrderService();
    }

    public static OrderService getInstance(){
        return OrderServiceHolder.ORDER_SERVICE_HOLDER;
    }

    public Order getOrder(Cart cart){
        Order order = new Order();
        order.setItems(cart.getCartItemList().stream().map(cartItem -> {
            try{
                return cartItem.clone();
            }catch (CloneNotSupportedException e){
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList()));
        order.setSubtotal(cart.getTotalCost());
        order.setDeliveryCost(calculateDeliveryCost());
        order.setTotalCost(order.getSubtotal().add(order.getDeliveryCost()));
        return order;
    }

    public void placeOrder(Order order){
        order.setSecureId(UUID.randomUUID().toString());
        orderDao.save(order);
    }

    public List<PaymentMethod> getPaymentMethods(){
        return Arrays.asList(PaymentMethod.values());
    }

    private BigDecimal calculateDeliveryCost(){
        return new BigDecimal(11);
    }
}
