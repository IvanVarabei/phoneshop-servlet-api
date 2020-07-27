package com.es.phoneshop.model.service;

import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.CartItem;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.OutOfStockException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
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
    private CartItem cartItem1;
    @Mock
    private CartItem cartItem2;
    @Mock
    private CartItem cartItem3;
    @Mock
    private Product product3;
    @Mock
    private HttpSession session;
    private List<CartItem> cartItems;
    @InjectMocks
    private CartService cartService = CartService.getInstance();

    @Before
    public void setup() {
        when(cartItem1.getProduct()).thenReturn(product1);
        when(cartItem2.getProduct()).thenReturn(product2);
        when(cartItem3.getProduct()).thenReturn(product3);
        when(cartItem3.getQuantity()).thenReturn(4);
        cartItems = new LinkedList<>();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);
        when(cart.getCartItemList()).thenReturn(cartItems);
        when(product1.getId()).thenReturn(1L);
        when(product2.getId()).thenReturn(2L);
        when(product3.getId()).thenReturn(3L);
        when(product3.getStock()).thenReturn(5);
        when(session.getAttribute("cart")).thenReturn(cart);
    }

    @Test
    public void testAdd() throws OutOfStockException {
        cartService.add(session, product3, 1);

        verify(cart).addCartItem(new CartItem(product3, 1));
    }

    @Test
    public void testAddAlreadyInCart() throws OutOfStockException {
        cartItems.add(cartItem3);

        cartService.add(session, product3, 1);

        verify(cartItem3).setQuantity(5);
        verify(cart, never()).addCartItem(new CartItem(product3, 1));
    }

    @Test(expected = OutOfStockException.class)
    public void testAddAlreadyInCartOutOfStock() throws OutOfStockException {
        cartItems.add(cartItem3);

        cartService.add(session, product3, 2);

        verify(cartItem3, never()).setQuantity(6);
        verify(cart, never()).addCartItem(new CartItem(product3, 2));
    }

    @Test
    public void testAddCartNull() throws OutOfStockException {
        when(session.getAttribute("cart")).thenReturn(null);

        try {
            cartService.add(session, product3, 1);
        } catch (NullPointerException ignored) {
        }

        verify(session).setAttribute(eq("cart"), any(Cart.class));
    }
}