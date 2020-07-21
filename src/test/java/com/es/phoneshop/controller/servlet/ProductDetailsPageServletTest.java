package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.ItemNotFoundException;
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
import java.io.IOException;

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
    private ArrayListProductDao arrayListProductDao;
    @Mock
    private Product product1;
    @InjectMocks
    private final ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() throws ItemNotFoundException {
        when(request.getPathInfo()).thenReturn("/1");
        when(arrayListProductDao.findProduct(1L)).thenReturn(product1);
        when(request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute("product", product1);
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoGetNonexistentProduct() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/blabla");

        servlet.doGet(request, response);

        verify(request).setAttribute("message", "Product with code 'blabla' not found.");
        verify(response, times(1)).sendError(404);
    }
}
