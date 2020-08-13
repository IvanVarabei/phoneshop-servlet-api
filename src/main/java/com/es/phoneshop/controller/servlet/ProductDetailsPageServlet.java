package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.controller.value.Const;
import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.model.service.RecentlyViewedService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Optional;

public class ProductDetailsPageServlet extends HttpServlet {
    private static final String REDIRECT_AFTER_ADDING_TO_CART = "%s/products/%s?message=Added to cart successfully";
    private static final String PRODUCT_DETAILS_JSP = "/WEB-INF/pages/productDetails.jsp";
    private ArrayListProductDao dao = ArrayListProductDao.getInstance();
    private CartService cartService = CartService.getInstance();
    private RecentlyViewedService recentlyViewedService = RecentlyViewedService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Product> product = attachProductOrSendError(req, resp);
        if (product.isPresent()) {
            req.getRequestDispatcher(PRODUCT_DETAILS_JSP).forward(req, resp);
            recentlyViewedService.updateRecentlyViewedLine(req.getSession(), product.get());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Product> product = attachProductOrSendError(req, resp);
        if (product.isEmpty()) {
            return;
        }
        Optional<Integer> quantity = extractQuantityOrForward(req, resp);
        if (quantity.isEmpty()) {
            return;
        }
        if (addProductToCartOrForward(req, resp, product.get(), quantity.get())) {
            resp.sendRedirect(String.format(REDIRECT_AFTER_ADDING_TO_CART, req.getContextPath(), product.get().getId()));
        }
    }

    private Optional<Product> attachProductOrSendError(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String productId = req.getPathInfo().substring(1);
        try {
            Product product = dao.findProduct(Long.valueOf(productId));
            req.setAttribute(Const.RequestAttribute.PRODUCT, product);
            return Optional.of(product);
        } catch (NumberFormatException | ItemNotFoundException e) {
            req.setAttribute(Const.RequestAttribute.MESSAGE, String.format(Const.ErrorInfo.NOT_FOUND, productId));
            resp.sendError(Const.ErrorInfo.PAGE_NOT_FOUND_CODE);
            return Optional.empty();
        }
    }

    private Optional<Integer> extractQuantityOrForward(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            return Optional.of(NumberFormat.getInstance(
                    req.getLocale()).parse(req.getParameter(Const.RequestParam.QUANTITY)).intValue());
        } catch (NumberFormatException | ParseException e) {
            req.setAttribute(Const.RequestAttribute.ERROR, Const.ErrorInfo.NOT_NUMBER);
            req.getRequestDispatcher(PRODUCT_DETAILS_JSP).forward(req, resp);
            return Optional.empty();
        }
    }

    private boolean addProductToCartOrForward(
            HttpServletRequest req, HttpServletResponse resp, Product product, int quantity)
            throws ServletException, IOException {
        try {
            cartService.add(req.getSession(), product, quantity);
            return true;
        } catch (OutOfStockException e) {
            req.setAttribute(Const.RequestAttribute.ERROR,
                    String.format(Const.ErrorInfo.NOT_ENOUGH_STOCK, e.getAvailableAmount()));
            req.getRequestDispatcher(PRODUCT_DETAILS_JSP).forward(req, resp);
            return false;
        }
    }
}
