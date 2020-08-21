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
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CheckoutPageServlet extends HttpServlet {
    private static final String CHECKOUT_JSP = "/WEB-INF/pages/checkout.jsp";
    private CartService cartService = CartService.getInstance();
    private OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = extractCartOrSetNewOne(req);
        req.setAttribute(Const.RequestAttribute.ORDER, orderService.createOrder(cart));
        req.setAttribute(Const.RequestAttribute.PAY_METHODS, orderService.getPaymentMethods());
        req.getRequestDispatcher(CHECKOUT_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order = orderService.createOrder(extractCartOrSetNewOne(req));
        Map<String, String> errors = new HashMap<>();
        setRequiredParameter(req, Const.RequestParam.FIRST_NAME, errors, order::setFirstName);
        setRequiredParameter(req, Const.RequestParam.LAST_NAME, errors, order::setLastName);
        setRequiredParameter(req, Const.RequestParam.PHONE, errors, order::setPhone);
        setRequiredParameter(req, Const.RequestParam.DELIVERY_ADDRESS, errors, order::setDeliveryAddress);
        setDate(req, errors, order);
        setPaymentMethod(req, errors, order);
        handleErrors(req, resp, errors, order);
    }

    private void handleErrors(HttpServletRequest req, HttpServletResponse resp, Map<String, String> errorAttributes,
                              Order order) throws IOException, ServletException {
        if (errorAttributes.isEmpty()) {
            orderService.placeOrder(order);
            cartService.clearCart(extractCartOrSetNewOne(req));
            resp.sendRedirect(req.getContextPath() + "/order/overview/" + order.getSecureId());
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

    private void setPaymentMethod(HttpServletRequest req, Map<String, String> errors, Order order) {
        String value = req.getParameter(Const.RequestParam.PAY_METHOD);
        if (value == null || value.isEmpty()) {
            errors.put(Const.RequestParam.PAY_METHOD, Const.ErrorInfo.VALUE_IS_REQUIRED);
        } else {
            order.setPaymentMethod(PaymentMethod.valueOf(value));
        }
    }

    private void setDate(HttpServletRequest req, Map<String, String> errors, Order order) {
        String value = req.getParameter(Const.RequestParam.DELIVERY_DATE);
        if (value == null || value.isEmpty()) {
            errors.put(Const.RequestParam.DELIVERY_DATE, Const.ErrorInfo.VALUE_IS_REQUIRED);
        } else {
            String date = req.getParameter(Const.RequestParam.DELIVERY_DATE);
            if (isLegalDate(date)) {
                LocalDate localDate = LocalDate.parse(date);
                order.setDeliveryDate(localDate);
            } else {
                errors.put(Const.RequestParam.DELIVERY_DATE, Const.ErrorInfo.INVALID_DATE_VALUE);
            }
        }
    }

    private Cart extractCartOrSetNewOne(HttpServletRequest req) {
        return req.getSession().getAttribute(Const.RequestAttribute.CART) == null ? new Cart() :
                (Cart) req.getSession().getAttribute(Const.RequestAttribute.CART);
    }

    private boolean isLegalDate(String potentialDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.parse(potentialDate, new ParsePosition(0)) != null;
    }
}
