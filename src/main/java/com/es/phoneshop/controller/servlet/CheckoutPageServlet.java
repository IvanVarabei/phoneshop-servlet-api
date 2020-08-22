package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.Order;
import com.es.phoneshop.model.entity.PaymentMethod;
import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.model.service.OrderService;
import com.es.phoneshop.model.validator.UserDataValidator;
import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CheckoutPageServlet extends HttpServlet {
    private static final String CHECKOUT_JSP = "/WEB-INF/pages/checkout.jsp";
    private static final String ORDER_OVERVIEW_REDIRECT = "%s/order/overview/%s";
    private CartService cartService = CartService.getInstance();
    private OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = cartService.extractCartOrSetNewOne(req.getSession());
        req.setAttribute(Const.RequestAttribute.ORDER, orderService.createOrder(cart));
        req.setAttribute(Const.RequestAttribute.PAY_METHODS, orderService.getPaymentMethods());
        req.getRequestDispatcher(CHECKOUT_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order = orderService.createOrder(cartService.extractCartOrSetNewOne(req.getSession()));
        Map<String, String> errors = new HashMap<>();
        setRequiredParameter(req, Const.RequestParam.FIRST_NAME, errors, order::setFirstName);
        setRequiredParameter(req, Const.RequestParam.LAST_NAME, errors, order::setLastName);
        setPhone(req, errors, order);
        setDate(req, errors, order);
        setRequiredParameter(req, Const.RequestParam.DELIVERY_ADDRESS, errors, order::setDeliveryAddress);
        setPaymentMethod(req, errors, order);
        handleErrors(req, resp, errors, order);
    }

    private void handleErrors(HttpServletRequest req, HttpServletResponse resp, Map<String, String> errorAttributes,
                              Order order) throws IOException, ServletException {
        if (errorAttributes.isEmpty()) {
            orderService.placeOrder(order);
            cartService.clearCart(cartService.extractCartOrSetNewOne(req.getSession()));
            resp.sendRedirect(String.format(ORDER_OVERVIEW_REDIRECT, req.getContextPath(), order.getSecureId()));
        } else {
            req.setAttribute(Const.RequestAttribute.ERRORS, errorAttributes);
            req.setAttribute(Const.RequestAttribute.ORDER, order);
            req.setAttribute(Const.RequestAttribute.PAY_METHODS, orderService.getPaymentMethods());
            req.getRequestDispatcher(CHECKOUT_JSP).forward(req, resp);
        }
    }

    private void setRequiredParameter(HttpServletRequest req, String parameter, Map<String, String> errors
            , Consumer<String> consumer) {
        String value = req.getParameter(parameter);
        if (value == null || value.isEmpty()) {
            errors.put(parameter, Const.ErrorInfo.VALUE_IS_REQUIRED);
        } else {
            consumer.accept(value);
        }
    }

    private void setPhone(HttpServletRequest req, Map<String, String> errors, Order order) {
        String potentialPhone = req.getParameter(Const.RequestParam.PHONE);
        if (UserDataValidator.isLegalPhone(potentialPhone)) {
            order.setPhone(potentialPhone);
        } else {
            errors.put(Const.RequestParam.PHONE, Const.ErrorInfo.INVALID_VALUE);
        }
    }

    private void setDate(HttpServletRequest req, Map<String, String> errors, Order order) {
        String potentialDate = req.getParameter(Const.RequestParam.DELIVERY_DATE);
        if (UserDataValidator.isLegalDate(potentialDate)) {
            order.setDeliveryDate(LocalDate.parse(potentialDate));
        } else {
            errors.put(Const.RequestParam.DELIVERY_DATE, Const.ErrorInfo.INVALID_VALUE);
        }
    }

    private void setPaymentMethod(HttpServletRequest req, Map<String, String> errors, Order order) {
        String value = req.getParameter(Const.RequestParam.PAY_METHOD);
        if (value == null || value.isEmpty()) {
            errors.put(Const.RequestParam.PAY_METHOD, Const.ErrorInfo.VALUE_IS_REQUIRED);
        } else {
            order.setPaymentMethod(PaymentMethod.valueOf(value));
        }
    }
}
