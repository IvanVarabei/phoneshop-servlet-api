package com.es.phoneshop.model.service;

import com.es.phoneshop.model.dao.OrderDao;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.entity.*;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.service.impl.DefaultOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultOrderServiceTest {
    @Mock
    private OrderDao orderDao;
    @Mock
    private ProductDao productDao;
    @Mock
    private Cart cart;
    @Mock
    private CartItem cartItem1;
    @Mock
    private CartItem cartItem2;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Order order;
    private final BigDecimal cartTotalCost = BigDecimal.valueOf(3);
    private List<CartItem> cartItemList;
    @InjectMocks
    private OrderService orderService = DefaultOrderService.getInstance();

    @Before
    public void setup() throws CloneNotSupportedException {
        cartItemList = new ArrayList<>();
        cartItemList.add(cartItem1);
        cartItemList.add(cartItem2);
        when(cartItem1.clone()).thenReturn(cartItem1);
        when(cartItem2.clone()).thenReturn(cartItem2);
        when(cart.getCartItemList()).thenReturn(cartItemList);
        when(cart.getTotalCost()).thenReturn(cartTotalCost);
        when(cartItem1.getProduct()).thenReturn(product1);
        when(cartItem2.getProduct()).thenReturn(product2);
        when(product1.getStock()).thenReturn(3);
        when(product2.getStock()).thenReturn(3);
        when(cartItem1.getQuantity()).thenReturn(1);
        when(cartItem2.getQuantity()).thenReturn(1);
        when(order.getCartItemList()).thenReturn(cartItemList);
    }

    @Test
    public void testCreateOrder() {
        Order expected = new Order();
        expected.setCartItemList(List.of(cartItem1, cartItem2));
        expected.setSubtotal(cartTotalCost);
        expected.setDeliveryCost(new BigDecimal(11));
        expected.setTotalCost(new BigDecimal(11).add(cartTotalCost));

        Order actual = orderService.createOrder(cart);

        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void testCreateOrderCloneError() throws CloneNotSupportedException {
        doThrow(CloneNotSupportedException.class).when(cartItem1).clone();

        orderService.createOrder(cart);
    }

    //todo
//    @Test
//    public void testPlaceOrder() {
//        orderService.placeOrder(order);
//
//        verify(order).setSecureId(anyString());
//        verify(orderDao).save(order);
//        verify(productDao).updateProductStock(product1, 2);
//        verify(productDao).updateProductStock(product2, 2);
//    }

    @Test
    public void testGetPaymentMethod() {
        List<PaymentMethod> expected = List.of(PaymentMethod.values());

        List<PaymentMethod> actual = orderService.getPaymentMethods();

        assertEquals(expected, actual);
    }

    @Test
    public void testFindOrderBySecureId() throws ItemNotFoundException {
        orderService.findOrderBySecureId("id");

        verify(orderDao).findOrderBySecureId("id");
    }
}
