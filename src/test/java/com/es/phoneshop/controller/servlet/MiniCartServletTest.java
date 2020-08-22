package com.es.phoneshop.controller.servlet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MiniCartServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    private MiniCartServlet servlet = new MiniCartServlet();

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/pages/miniCart.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testService() throws ServletException, IOException {
        servlet.service(request, response);

        verify(request).setAttribute(eq("cart"), any());
        verify(requestDispatcher).include(request, response);
    }
}
