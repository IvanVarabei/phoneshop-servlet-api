package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListOrderDaoTest {
    @Mock
    private Order order1;
    @Mock
    private Order order2;
    @Mock
    private Order order3;
    private List<Order> orderList;
    private final ArrayListOrderDao dao = ArrayListOrderDao.getInstance();

    @Before
    public void setup() {
        orderList = new ArrayList<>();
        dao.setItems(orderList);
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        when(order1.getSecureId()).thenReturn("1s");
        when(order2.getSecureId()).thenReturn("2s");
        when(order3.getSecureId()).thenReturn("3s");
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
}
