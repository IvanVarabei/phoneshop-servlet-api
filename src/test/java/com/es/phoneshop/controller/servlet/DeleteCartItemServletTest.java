package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.model.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeleteCartItemServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private Product product1;
    @Mock
    private ProductService productService;
    @Mock
    private CartService cartService;
    @InjectMocks
    private final DeleteCartItemServlet servlet = new DeleteCartItemServlet();

    @Before
    public void setup() throws ItemNotFoundException {
        when(request.getPathInfo()).thenReturn("/1");
        when(productService.find(1L)).thenReturn(product1);
    }

    @Test
    public void testDoPost() throws IOException {
        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString() + "/cart?message=Cart item removed successfully");
    }

    @Test
    public void testDoPostItemNotFound() throws IOException, ItemNotFoundException {
        doThrow(ItemNotFoundException.class).when(productService).find(1L);

        servlet.doPost(request, response);

        verify(response, never()).sendRedirect(anyString());
    }
}
