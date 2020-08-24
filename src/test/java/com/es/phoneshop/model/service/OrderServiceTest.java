package com.es.phoneshop.model.service;

import com.es.phoneshop.model.dao.OrderDao;
import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.CartItem;
import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.entity.PaymentMethod;
import com.es.phoneshop.model.exception.ItemNotFoundException;
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
public class OrderServiceTest {
    @Mock
    private OrderDao dao;
    @Mock
    private Cart cart;
    @Mock
    private CartItem cartItem1;
    @Mock
    private CartItem cartItem2;
    @Mock
    private Order order;
    private final BigDecimal cartTotalCost = BigDecimal.valueOf(3);
    private List<CartItem> cartItemList;
    @InjectMocks
    private OrderService orderService = OrderService.getInstance();

    @Before
    public void setup() throws CloneNotSupportedException {
        cartItemList = new ArrayList<>();
        cartItemList.add(cartItem1);
        cartItemList.add(cartItem2);
        when(cartItem1.clone()).thenReturn(cartItem1);
        when(cartItem2.clone()).thenReturn(cartItem2);
        when(cart.getCartItemList()).thenReturn(cartItemList);
        when(cart.getTotalCost()).thenReturn(cartTotalCost);
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

    @Test
    public void testPlaceOrder() {
        orderService.placeOrder(order);

        verify(order).setSecureId(anyString());
        verify(dao).save(order);
        verify(dao).subtractProductQuantityRegardingPlacedOrder(order);
    }

    @Test
    public void testGetPaymentMethod() {
        List<PaymentMethod> expected = List.of(PaymentMethod.values());

        List<PaymentMethod> actual = orderService.getPaymentMethods();

        assertEquals(expected, actual);
    }

    @Test
    public void testFindOrderById() throws ItemNotFoundException {
        orderService.findOrder(1L);

        verify(dao).find(1L);
    }

    @Test
    public void testFindOrderBySecureId() throws ItemNotFoundException {
        orderService.findOrderBySecureId("id");

        verify(dao).findOrderBySecureId("id");
    }
}
