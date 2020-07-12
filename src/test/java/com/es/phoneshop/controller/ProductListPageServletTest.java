package com.es.phoneshop.controller;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    private final ProductListPageServlet servlet = new ProductListPageServlet();

    @Before
    public void setup() throws ServletException, IOException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
    }

    @Test
    public void testDoGetRequestSetAttribute() {
        verify(request).setAttribute("products", new ArrayListProductDao().findProducts());
    }

    @Test
    public void testDoGetForward() throws ServletException, IOException {
        verify(requestDispatcher, times(1)).forward(request, response);
    }
}