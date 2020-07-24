package com.es.phoneshop.model.service;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.CartItem;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.exception.OutOfStockException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {
    @Mock
    private Cart cart;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private ArrayListProductDao dao;
    private List<CartItem> cartItems;
    @InjectMocks
    private CartService cartService = CartService.getInstance();

    @Before
    public void setup() throws ItemNotFoundException {
        cartItems = new LinkedList<>();
        cartItems.add(new CartItem(product1, 3));
        cartItems.add(new CartItem(product2, 2));
        when(cart.getCartItemList()).thenReturn(cartItems);
        when(product1.getId()).thenReturn(1L);
        when(product2.getId()).thenReturn(2L);
        when(product3.getId()).thenReturn(3L);
        when(product3.getStock()).thenReturn(5);
        when(dao.findProduct(3L)).thenReturn(product3);
    }

    @Test
    public void testAdd() throws OutOfStockException {
        cartService.add(cart, 3L, 1);

        verify(cart).add(new CartItem(product3, 1));
    }

    @Test
    public void testAddAlreadyInCart() throws OutOfStockException {
        cartItems.add(new CartItem(product3, 4));

        cartService.add(cart, 3L, 1);

        verify(cart).add(new CartItem(product3, 1));
    }

    @Test(expected = OutOfStockException.class)
    public void testAddAlreadyInCartOutOfStock() throws OutOfStockException {
        cartItems.add(new CartItem(product3, 4));

        cartService.add(cart, 3L, 2);

        verify(cart, never()).add(new CartItem(product3, 2));
    }

    @Test(expected = OutOfStockException.class)
    public void testAddOutOfStock() throws OutOfStockException {
        cartService.add(cart, 3L, 9);

        verify(cart, never()).add(new CartItem(product3, 9));
    }
}
