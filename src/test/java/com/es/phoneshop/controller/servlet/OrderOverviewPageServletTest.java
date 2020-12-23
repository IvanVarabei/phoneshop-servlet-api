package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.service.OrderService;
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
public class OrderOverviewPageServletTest {
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private OrderService orderService;
    @Mock
    private Order order;
    @InjectMocks
    private final OrderOverviewPageServlet servlet = new OrderOverviewPageServlet();

    @Before
    public void setup() throws ItemNotFoundException {
        when(req.getPathInfo()).thenReturn("/some-id");
        when((orderService.findOrderBySecureId("some-id"))).thenReturn(order);
        when(req.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp")).thenReturn(requestDispatcher);
    }
    @Test
    public void doGetOrderFoundForward() throws ServletException, IOException {
        servlet.doGet(req, resp);

        verify(req).setAttribute("order", order);
        verify(requestDispatcher).forward(req, resp);
    }
    @Test
    public void doGetOrderNotFoundSendError() throws ServletException, IOException, ItemNotFoundException {
        doThrow(ItemNotFoundException.class).when(orderService).findOrderBySecureId("some-id");

        servlet.doGet(req, resp);

        verify(req).setAttribute("message", "Order with code 'some-id' not found");
        verify(resp).sendError(404);
    }
}
