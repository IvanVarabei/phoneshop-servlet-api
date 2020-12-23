package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.service.ProductService;
import com.es.phoneshop.model.service.impl.DefaultProductService;
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
public class EditProductPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private final ProductService productService = DefaultProductService.getInstance();
    @InjectMocks
    private final EditProductPageServlet servlet = new EditProductPageServlet();

    @Before
    public void setup() {
        when(request.getContextPath()).thenReturn("contextPath");
        when(request.getParameterValues("productId")).thenReturn(new String[]{"1", "2"});
        when(request.getParameterValues("imageUrl")).thenReturn(new String[]{"1url", "2url"});
        when(request.getParameterValues("tag")).thenReturn(new String[]{"htc", "palm"});
        when(request.getParameterValues("description")).thenReturn(new String[]{"d1", "d2"});
        when(request.getParameterValues("price")).thenReturn(new String[]{"1", "2"});
        when(request.getParameterValues("stock")).thenReturn(new String[]{"1", "2"});
        when(request.getRequestDispatcher("WEB-INF/pages/editProduct.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void doPostNoErrorsUpdateProducts() throws ServletException, IOException {
        servlet.doPost(request, response);

        verify(productService).updateImageUrl(1, "1url");
        verify(productService).updateTag(1, "htc");
        verify(productService).updateDescription(1, "d1");
        verify(productService).updatePrice(1, 1);
        verify(productService).updateStock(1, 1);
        verify(productService).updateImageUrl(2, "2url");
        verify(productService).updateTag(2, "palm");
        verify(productService).updateDescription(2, "d2");
        verify(productService).updatePrice(2, 2);
        verify(productService).updateStock(2, 2);
        verify(response).sendRedirect("contextPath/editProduct");
    }

    @Test
    public void doPostFirstProductHasWrongPriceWillNotBeUpdated() throws ServletException, IOException {
        when(request.getParameterValues("price")).thenReturn(new String[]{"notParsed", "2"});

        servlet.doPost(request, response);

        verify(productService).updateImageUrl(1, "1url");
        verify(productService).updateTag(1, "htc");
        verify(productService).updateDescription(1, "d1");
        verify(productService, never()).updatePrice(eq(1), anyDouble());
        verify(productService).updateStock(1, 1);
        verify(productService).updateImageUrl(2, "2url");
        verify(productService).updateTag(2, "palm");
        verify(productService).updateDescription(2, "d2");
        verify(productService).updatePrice(2, 2);
        verify(productService).updateStock(2, 2);
        verify(requestDispatcher).forward(request, response);
    }
}
