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
import java.util.Queue;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ArrayListProductDao dao;
    @Mock
    private CartService cartService;
    @Mock
    private Product product1;
    @Mock
    private HttpSession session;
    @InjectMocks
    private final ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() throws ItemNotFoundException {
        when(request.getPathInfo()).thenReturn("/1");
        when(dao.find(1L)).thenReturn(product1);
        when(product1.getId()).thenReturn(1L);
        when(request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp")).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        when(request.getContextPath()).thenReturn("/phoneshop_servlet_api_war_exploded");
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute("product", product1);
        verify(requestDispatcher).forward(request, response);
        verify(session).setAttribute(eq("recent"), any(Queue.class));
    }

    @Test
    public void testDoGetNonexistentProduct() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/blabla");

        servlet.doGet(request, response);

        verify(request).setAttribute("message", "Product with code 'blabla' not found.");
        verify(response, times(1)).sendError(404);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getParameter("quantity")).thenReturn("1");

        servlet.doPost(request, response);

        verify(response).sendRedirect(
                "/phoneshop_servlet_api_war_exploded/products/1?message=Added to cart successfully");
    }

    @Test
    public void testDoPostNonexistentProduct() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/blabla");

        servlet.doPost(request, response);

        verify(request).setAttribute("message", "Product with code 'blabla' not found.");
        verify(response, never()).sendRedirect(
                "/phoneshop_servlet_api_war_exploded/products/1?message=Added to cart successfully");
    }

    @Test
    public void testDoPostWrongQuantity() throws ServletException, IOException {
        when(request.getParameter("quantity")).thenReturn("blabla");

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Not a number.");
        verify(response, never()).sendRedirect(
                "/phoneshop_servlet_api_war_exploded/products/1?message=Added to cart successfully");
    }

    @Test
    public void testDoPostOutOfStock() throws ServletException, IOException, OutOfStockException {
        when(request.getParameter("quantity")).thenReturn("1");
        doThrow(OutOfStockException.class).when(cartService).add(session, product1, 1);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), String.format("Not enough stock. Available:%s", anyString()));
        verify(response, never()).sendRedirect(
                "/phoneshop_servlet_api_war_exploded/products/1?message=Added to cart successfully");
    }
}
