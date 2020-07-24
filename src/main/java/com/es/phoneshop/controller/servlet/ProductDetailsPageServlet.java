package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.model.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.Queue;

public class ProductDetailsPageServlet extends HttpServlet {
    private static final String ERROR_NOT_FOUND = "Product with code '%s' not found.";
    private static final String ERROR_NOT_ENOUGH_STOCK = "Not enough stock. Available:%s";
    private static final String ERROR_NOT_NUMBER = "Not a number";
    private static final String REQUEST_ATTRIBUTE_PRODUCT = "product";
    private static final String REQUEST_ATTRIBUTE_MESSAGE = "message";
    private static final String REQUEST_ATTRIBUTE_ERROR = "error";
    private static final String REQUEST_ATTRIBUTE_ADDING_TO_CART_MESSAGE = "show";
    private static final String REQUEST_PARAM_QUANTITY = "quantity";
    private static final String SESSION_ATTRIBUTE_RECENT = "recent";
    private static final String SESSION_ATTRIBUTE_CART = "cart";
    private static final String REDIRECT_AFTER_ADDING_TO_CART = "%s/products/%s?message=Added to cart successfully";
    private static final String PRODUCT_DETAILS_PATH = "/WEB-INF/pages/productDetails.jsp";
    private static final int NOT_FOUND_ERROR_CODE = 404;
    private static final int RECENT_VIEWED_AMOUNT = 3;
    private ArrayListProductDao dao = ArrayListProductDao.getInstance();
    private CartService cartService = CartService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getPathInfo().substring(1);
        HttpSession session = request.getSession();
        try {
            request.setAttribute(REQUEST_ATTRIBUTE_PRODUCT, dao.findProduct(Long.valueOf(productId)));
            request.getRequestDispatcher(PRODUCT_DETAILS_PATH).forward(request, response);
        } catch (NumberFormatException | ItemNotFoundException e) {
            request.setAttribute(REQUEST_ATTRIBUTE_MESSAGE, String.format(ERROR_NOT_FOUND, productId));
            response.sendError(NOT_FOUND_ERROR_CODE);
            return;
        }
        Queue<Product> recent = session.getAttribute(SESSION_ATTRIBUTE_RECENT) == null ? new LinkedList<>() :
                (Queue<Product>) session.getAttribute(SESSION_ATTRIBUTE_RECENT);
        try {
            if (!recent.contains(dao.findProduct(Long.valueOf(productId)))) {
                recent.offer(dao.findProduct(Long.valueOf(productId)));
            }
            if (recent.size() > RECENT_VIEWED_AMOUNT) {
                recent.poll();
            }
            session.setAttribute(SESSION_ATTRIBUTE_RECENT, recent);
        } catch (ItemNotFoundException ignored) {
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int quantity;
        long productId = Long.parseLong(req.getPathInfo().substring(1));
        try {
            quantity = NumberFormat.getInstance(
                    req.getLocale()).parse(req.getParameter(REQUEST_PARAM_QUANTITY)).intValue();
        } catch (NumberFormatException | ParseException e) {
            req.setAttribute(REQUEST_ATTRIBUTE_ADDING_TO_CART_MESSAGE, false);
            req.setAttribute(REQUEST_ATTRIBUTE_ERROR, ERROR_NOT_NUMBER);
            doGet(req, resp);
            return;
        }
        try {
            HttpSession session = req.getSession();
            if (session.getAttribute(SESSION_ATTRIBUTE_CART) == null) {
                session.setAttribute(SESSION_ATTRIBUTE_CART, new Cart());
            }
            cartService.add((Cart) session.getAttribute(SESSION_ATTRIBUTE_CART), productId, quantity);
        } catch (OutOfStockException e) {
            req.setAttribute(REQUEST_ATTRIBUTE_ADDING_TO_CART_MESSAGE, false);
            req.setAttribute(REQUEST_ATTRIBUTE_ERROR, String.format(ERROR_NOT_ENOUGH_STOCK, e.getAvailableAmount()));
            doGet(req, resp);
            return;
        }
        resp.sendRedirect(String.format(REDIRECT_AFTER_ADDING_TO_CART, req.getContextPath(), productId));
    }
}
