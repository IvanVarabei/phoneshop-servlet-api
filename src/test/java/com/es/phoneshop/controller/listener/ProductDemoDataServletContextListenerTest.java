package com.es.phoneshop.controller.listener;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemoDataServletContextListenerTest {
    @Mock
    private ServletContextEvent servletContextEvent;
    @Mock
    private ServletContext servletContext;
    @Mock
    private ArrayListProductDao arrayListProductDao;
    @InjectMocks
    private final ProductDemoDataServletContextListener listener = new ProductDemoDataServletContextListener();

    @Before
    public void setup() {
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter("isInsertDemoData")).thenReturn("true");
    }

    @Test
    public void testContextInitialized() {
        listener.contextInitialized(servletContextEvent);

        verify(arrayListProductDao, atLeastOnce()).save(any());
    }

    @Test
    public void testContextInitializedFalse() {
        when(servletContext.getInitParameter("isInsertDemoData")).thenReturn("false");

        listener.contextInitialized(servletContextEvent);

        verify(arrayListProductDao, never()).save(any());
    }
}
