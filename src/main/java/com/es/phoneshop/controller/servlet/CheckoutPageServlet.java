package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.entity.PaymentMethod;
import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.model.service.OrderService;
import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CheckoutPageServlet extends HttpServlet {
    private static final String CHECKOUT_JSP = "/WEB-INF/pages/checkout.jsp";
    private CartService cartService = CartService.getInstance();
    private OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = req.getSession().getAttribute(Const.RequestAttribute.CART) == null ? new Cart() :
                (Cart) req.getSession().getAttribute(Const.RequestAttribute.CART);
        req.setAttribute("order", orderService.getOrder(cart));
        req.setAttribute(Const.RequestAttribute.PAY_METHODS, orderService.getPaymentMethods());
        req.getRequestDispatcher(CHECKOUT_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = req.getSession().getAttribute(Const.RequestAttribute.CART) == null ? new Cart() :
                (Cart) req.getSession().getAttribute(Const.RequestAttribute.CART);
        Order order = orderService.getOrder(cart);
        Map<String, String> errors = new HashMap<>();
        setRequiredParameter(req, "firstName", errors, order::setFirstName);
        setRequiredParameter(req, "lastName", errors, order::setLastName);
        setRequiredParameter(req, "phone", errors, order::setPhone);
        setRequiredParameter(req, "deliveryAddress", errors, order::setDeliveryAddress);
        setPaymentMethod(req, errors, order);
        handleErrors(req, resp, errors, order);
    }

    private void handleErrors(HttpServletRequest req, HttpServletResponse resp, Map<String, String> errorAttributes,
                              Order order) throws IOException, ServletException {
        if (errorAttributes.isEmpty()) {
            orderService.placeOrder(order);
            //cartService.clearCart();
            resp.sendRedirect(req.getContextPath() + "/order/overview/" + order.getSecureId());
        } else {
            req.setAttribute(Const.RequestAttribute.ERRORS, errorAttributes);
            req.setAttribute("order", order);
            req.setAttribute(Const.RequestAttribute.PAY_METHODS, orderService.getPaymentMethods());
            req.getRequestDispatcher(CHECKOUT_JSP).forward(req, resp);
        }
    }

    private void setRequiredParameter(HttpServletRequest req, String parameter, Map<String, String> errors
            , Consumer<String> consumer) {
        String value = req.getParameter(parameter);
        if (value == null || value.isEmpty()) {
            errors.put(parameter, "Value is required");
        } else {
            consumer.accept(value);
        }
    }

    private void setPaymentMethod(HttpServletRequest req, Map<String, String> errors, Order order) {
        String value = req.getParameter(Const.RequestParam.PAY_METHOD);
        if (value == null || value.isEmpty()) {
            errors.put(Const.RequestParam.PAY_METHOD, "Value is required");
        } else {
            order.setPaymentMethod(PaymentMethod.valueOf(value));
        }
    }
}
