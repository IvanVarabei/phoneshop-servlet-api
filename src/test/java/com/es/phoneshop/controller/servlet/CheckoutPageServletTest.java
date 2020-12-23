package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.entity.PaymentMethod;
import com.es.phoneshop.model.service.CartService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private OrderService orderService;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;
    @Mock
    private Order order;
    @InjectMocks
    private final CheckoutPageServlet servlet = new CheckoutPageServlet();

    @Before
    public void setup() {
        when(req.getSession()).thenReturn(session);
        when(cartService.extractCartOrSetNewOne(session)).thenReturn(cart);
        when(orderService.createOrder(cart)).thenReturn(order);
        when((orderService.getPaymentMethods())).thenReturn(List.of(PaymentMethod.values()));
        when(req.getRequestDispatcher("/WEB-INF/pages/checkout.jsp")).thenReturn(requestDispatcher);
        when(req.getParameter("firstName")).thenReturn("Ivan");
        when(req.getParameter("lastName")).thenReturn("Varabei");
        when(req.getParameter("phone")).thenReturn("+375297324595");
        when(req.getParameter("deliveryDate")).thenReturn("1999-10-22");
        when(req.getParameter("deliveryAddress")).thenReturn("Minsk");
        when(req.getParameter("paymentMethod")).thenReturn(PaymentMethod.CASH.toString());
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(req, resp);

        verify(req).setAttribute("order", order);
        verify(req).setAttribute("paymentMethods", List.of(PaymentMethod.values()));
        verify(requestDispatcher).forward(req, resp);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(req, resp);

        verify(order).setFirstName("Ivan");
        verify(order).setLastName("Varabei");
        verify(order).setPhone("+375297324595");
        verify(order).setDeliveryDate(LocalDate.of(1999, 10, 22));
        verify(order).setDeliveryAddress("Minsk");
        verify(order).setPaymentMethod(PaymentMethod.CASH);
        verify(orderService).placeOrder(order);
        verify(cartService).clearCart(cart);
        verify(resp).sendRedirect(anyString());
    }

    @Test
    public void testDoPostInvalidFirstName() throws ServletException, IOException {
        when(req.getParameter("firstName")).thenReturn("");

        servlet.doPost(req, resp);
        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("firstName", "Value is required");

        verify(req).setAttribute("errors", expectedErrors);
        verify(req).setAttribute("order", order);
        verify(req).setAttribute("paymentMethods", List.of(PaymentMethod.values()));
        verify(requestDispatcher).forward(req, resp);
    }

    @Test
    public void testDoPostInvalidPhone() throws ServletException, IOException {
        when(req.getParameter("phone")).thenReturn("");

        servlet.doPost(req, resp);
        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("phone", "Invalid value");

        verify(req).setAttribute("errors", expectedErrors);
    }

    @Test
    public void testDoPostInvalidDate() throws ServletException, IOException {
        when(req.getParameter("deliveryDate")).thenReturn("");

        servlet.doPost(req, resp);
        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("deliveryDate", "Invalid value");

        verify(req).setAttribute("errors", expectedErrors);
    }

    @Test
    public void testDoPostEmptyPaymentMethod() throws ServletException, IOException {
        when(req.getParameter("paymentMethod")).thenReturn("");

        servlet.doPost(req, resp);
        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("paymentMethod", "Value is required");

        verify(req).setAttribute("errors", expectedErrors);
    }
}
