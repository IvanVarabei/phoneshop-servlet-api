package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
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
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private ProductDao productDao;
    @Mock
    private CartService cartService;
    @Mock
    private HttpSession session;
    @InjectMocks
    private final ProductListPageServlet servlet = new ProductListPageServlet();

    @Before
    public void setup() throws ItemNotFoundException {
        when(request.getSession()).thenReturn(session);
        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        when(request.getParameter("query")).thenReturn("");
        when(request.getParameter("productId")).thenReturn("1");
        when(request.getParameter("quantity")).thenReturn("1");
        when(productDao.find(1L)).thenReturn(product1);
        when(productDao.findProducts("", SortField.DEFAULT, SortOrder.DEFAULT))
                .thenReturn(List.of(product1, product2));
        when(productDao.findProducts("", SortField.PRICE, SortOrder.ASC))
                .thenReturn(List.of(product2, product1));
        when(request.getRequestDispatcher("/WEB-INF/pages/productList.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGetEmptySortFieldEmptySortOrder() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute("products", List.of(product1, product2));
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getParameter("sortField")).thenReturn("price");
        when(request.getParameter("sortOrder")).thenReturn("asc");

        servlet.doGet(request, response);

        verify(request).setAttribute("products", List.of(product2, product1));
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException, ItemNotFoundException, OutOfStockException {
        servlet.doPost(request, response);

        verify(productDao).find(1L);
        verify(cartService).add(session, product1, 1);
    }

    @Test
    public void testDoPostProductNotFound() throws ItemNotFoundException, ServletException, IOException {
        when(productDao.find(1L)).thenThrow(ItemNotFoundException.class);

        servlet.doPost(request, response);

        verify(request).setAttribute("message", "Product with code '1' not found.");
        verify(response).sendError(404);
    }

    @Test
    public void testDoPostWrongQuantity() throws ServletException, IOException {
        when(request.getParameter("quantity")).thenReturn("bla");

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Not a number.");
    }

    @Test
    public void testDoPostNotEnoughStock() throws ServletException, IOException, OutOfStockException {
        doThrow(OutOfStockException.class).when(cartService).add(session, product1, 1);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), anyString());
    }
}