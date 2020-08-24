package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.entity.CartItem;
import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListOrderDaoTest {
    @Mock
    private Order order1;
    @Mock
    private Order order2;
    @Mock
    private Order order3;
    @Mock
    private CartItem cartItem1;
    @Mock
    private CartItem cartItem2;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    private List<Order> orderList;
    private List<CartItem> itemList;
    private final ArrayListOrderDao dao = ArrayListOrderDao.getInstance();

    @Before
    public void setup() {
        orderList = new ArrayList<>();
        itemList = new ArrayList<>();
        dao.setItems(orderList);
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        when(order1.getSecureId()).thenReturn("1s");
        when(order2.getSecureId()).thenReturn("2s");
        when(order3.getSecureId()).thenReturn("3s");
        when(cartItem1.getProduct()).thenReturn(product1);
        when(cartItem2.getProduct()).thenReturn(product2);
        when(product1.getStock()).thenReturn(3);
        when(product2.getStock()).thenReturn(3);
        when(cartItem1.getQuantity()).thenReturn(1);
        when(cartItem2.getQuantity()).thenReturn(1);
        itemList.add(cartItem1);
        itemList.add(cartItem2);
        when(order1.getCartItemList()).thenReturn(itemList);
    }

    @Test
    public void testFindOrderBySecuredId() throws ItemNotFoundException {
        Order expected = order2;
        Order actual = dao.findOrderBySecureId("2s");

        assertEquals(expected, actual);
    }

    @Test(expected = ItemNotFoundException.class)
    public void testFindOrderBySecuredIdNotFound() throws ItemNotFoundException {
        dao.findOrderBySecureId("does not exist");
    }

    @Test
    public void testSubtractProductQuantityRegardingPlacedOrder(){
        dao.subtractProductQuantityRegardingPlacedOrder(order1);

        verify(product1).setStock(2);
        verify(product2).setStock(2);
    }
}
