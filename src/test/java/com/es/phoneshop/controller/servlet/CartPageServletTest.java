package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.model.service.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ArrayListProductDao dao;
    @Mock
    private CartService cartService;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private HttpSession session;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @InjectMocks
    private final CartPageServlet servlet = new CartPageServlet();

    @Before
    public void setup() throws ItemNotFoundException {
        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/pages/cart.jsp")).thenReturn(requestDispatcher);
        when(request.getParameterValues("productId")).thenReturn(new String[]{"1", "2"});
        when(request.getParameterValues("quantity")).thenReturn(new String[]{"1", "2"});
        when(dao.findProduct(1L)).thenReturn(product1);
        when(dao.findProduct(2L)).thenReturn(product2);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostItemNotFound() throws ServletException, IOException, OutOfStockException {
        doThrow(OutOfStockException.class).when(cartService).update(session, product1, 1);

        servlet.doPost(request, response);

        verify(response, never()).sendRedirect(anyString());
    }
}
