package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.controller.servlet.ProductListPageServlet;
import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.entity.Product;
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
import java.util.List;

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
    private ArrayListProductDao arrayListProductDao;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @InjectMocks
    private final ProductListPageServlet servlet = new ProductListPageServlet();

    @Before
    public void setup() {
       // when(arrayListProductDao.findProducts("")).thenReturn(List.of(product1, product2));
        when(request.getRequestDispatcher("/WEB-INF/pages/productList.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute("products", List.of(product1, product2));
        verify(requestDispatcher, times(1)).forward(request, response);
    }
}