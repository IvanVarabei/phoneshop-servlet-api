package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.model.service.ProductService;
import com.es.phoneshop.model.service.impl.DefaultCartService;
import com.es.phoneshop.model.service.impl.DefaultProductService;
import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {
    private static final String CART_JSP = "WEB-INF/pages/cart.jsp";
    private static final String REDIRECT_AFTER_UPDATING = "%s/cart?message=Cart updated successfully";
    private ProductService productService = DefaultProductService.getInstance();
    private CartService cartService = DefaultCartService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(CART_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] productIds = req.getParameterValues(Const.RequestParam.PRODUCT_ID);
        String[] quantities = req.getParameterValues(Const.RequestParam.QUANTITY);
        Map<Long, String> errors = new HashMap<>();
        for (int i = 0; i < productIds.length; i++) {
            long productId = Long.parseLong(productIds[i]);
            try {
                int quantity = NumberFormat.getInstance(req.getLocale()).parse(quantities[i]).intValue();
                cartService.update(req.getSession(), productService.find(productId), quantity);
            } catch (ItemNotFoundException | OutOfStockException | ParseException e) {
                handleError(errors, productId, e);
            }
        }
        if (errors.isEmpty()) {
            resp.sendRedirect(String.format(REDIRECT_AFTER_UPDATING, req.getContextPath()));
        } else {
            req.setAttribute(Const.AttributeKey.ERRORS, errors);
            req.getRequestDispatcher(CART_JSP).forward(req, resp);
        }
    }

    private void handleError(Map<Long, String> errors, Long productId, Exception e) {
        errors.put(productId, e.getClass().equals(ParseException.class) ? Const.ErrorInfo.NOT_NUMBER :
                String.format(Const.ErrorInfo.NOT_ENOUGH_STOCK, ((OutOfStockException) e).getAvailableAmount()));
    }
}
