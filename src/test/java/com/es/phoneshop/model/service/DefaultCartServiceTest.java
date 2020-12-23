package com.es.phoneshop.model.service;

import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.CartItem;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.model.service.impl.DefaultCartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCartServiceTest {
    private final BigDecimal price1 = BigDecimal.valueOf(1);
    private final BigDecimal price2 = BigDecimal.valueOf(2);
    private final BigDecimal price3 = BigDecimal.valueOf(3);
    @Mock
    private Cart cart;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private CartItem cartItem1;
    @Mock
    private CartItem cartItem2;
    @Mock
    private CartItem cartItem3;
    @Mock
    private HttpSession session;
    private List<CartItem> cartItems;
    private CartService cartService = DefaultCartService.getInstance();

    @Before
    public void setup() {
        when(cartItem1.getProduct()).thenReturn(product1);
        when(cartItem2.getProduct()).thenReturn(product2);
        when(cartItem3.getProduct()).thenReturn(product3);
        when(cartItem1.getQuantity()).thenReturn(1);
        when(cartItem2.getQuantity()).thenReturn(1);
        when(cartItem3.getQuantity()).thenReturn(4);
        cartItems = new LinkedList<>();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);
        when(cart.getCartItemList()).thenReturn(cartItems);
        when(product1.getId()).thenReturn(1L);
        when(product2.getId()).thenReturn(2L);
        when(product3.getId()).thenReturn(3L);
        when(product1.getPrice()).thenReturn(price1);
        when(product2.getPrice()).thenReturn(price2);
        when(product3.getPrice()).thenReturn(price3);
        when(product3.getStock()).thenReturn(5);
        when(session.getAttribute("cart")).thenReturn(cart);
    }

    @Test
    public void testAdd() throws OutOfStockException {
        cartService.add(session, product3, 1);
        List<CartItem> actual = cartItems;
        List<CartItem> expected = List.of(cartItem1, cartItem2, new CartItem(product3, 1));

        assertEquals(expected, actual);
    }

    @Test
    public void testAddAlreadyInCart() throws OutOfStockException {
        cartItems.add(cartItem3);

        cartService.add(session, product3, 1);

        verify(cartItem3).setQuantity(5);
    }

    @Test(expected = OutOfStockException.class)
    public void testAddAlreadyInCartOutOfStock() throws OutOfStockException {
        cartItems.add(cartItem3);

        cartService.add(session, product3, 2);

        verify(cartItem3, never()).setQuantity(6);
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

    @Test(expected = OutOfStockException.class)
    public void testUpdate() throws OutOfStockException {
        cartService.update(session, product3, 0);
    }

    @Test
    public void testDelete() {
        cartItems.add(cartItem3);
        cartService.delete(session, product2);
        List<CartItem> actual = cartItems;
        List<CartItem> expected = List.of(cartItem1, cartItem3);

        assertEquals(expected, actual);
        verify(session, times(2)).getAttribute("cart");
        verify(cart, times(2)).getCartItemList();
    }

    @Test
    public void testClearCart() {
        cartItems.add(cartItem3);
        cartService.clearCart(cart);

        assertTrue(cartItems.isEmpty());
    }
}
