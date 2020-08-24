package com.es.phoneshop.controller.listener;

import com.es.phoneshop.model.dao.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemoDataServletContextListenerTest {
    @Mock
    private ServletContextEvent servletContextEvent;
    @Mock
    private ServletContext servletContext;
    @Mock
    private ProductDao productDao;
    @InjectMocks
    private final ServletContextListener listener = new ProductDemoDataServletContextListener();

    @Before
    public void setup() {
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter("isInsertDemoData")).thenReturn("true");
    }

    @Test
    public void testContextInitialized() {
        listener.contextInitialized(servletContextEvent);

        verify(productDao, atLeastOnce()).save(any());
    }

    @Test
    public void testContextInitializedFalse() {
        when(servletContext.getInitParameter("isInsertDemoData")).thenReturn("false");

        listener.contextInitialized(servletContextEvent);

        verify(productDao, never()).save(any());
    }
}
