package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.model.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Optional;

public class ProductListPageServlet extends HttpServlet {
    private static final String SEARCH_QUERY_PARAM = "query";
    private static final String SORT_FIELD_PARAM = "sortField";
    private static final String SORT_ORDER_PARAM = "sortOrder";
    private static final String PRODUCT_LIST_ATTRIBUTE = "products";
    private static final String PRODUCT_LIST_PATH = "/WEB-INF/pages/productList.jsp";

    private static final String ERROR_NOT_FOUND = "Product with code '%s' not found.";
    private static final String ERROR_NOT_ENOUGH_STOCK = "Not enough stock. Available:%s";
    private static final String ERROR_NOT_NUMBER = "Not a number";
    private static final String REQUEST_ATTRIBUTE_PRODUCT = "product";
    private static final String REQUEST_ATTRIBUTE_MESSAGE = "message";
    private static final String REQUEST_ATTRIBUTE_ERROR = "error";
    private static final String REQUEST_PARAM_QUANTITY = "quantity";
    private static final String REDIRECT_AFTER_ADDING_TO_CART = "%s/products?query=%s&sortField=%s&sortOrder=%s&message=Added to cart successfully";
    private static final int NOT_FOUND_ERROR_CODE = 404;
    private ArrayListProductDao dao = ArrayListProductDao.getInstance();
    private CartService cartService = CartService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter(SEARCH_QUERY_PARAM) != null ? req.getParameter(SEARCH_QUERY_PARAM) : "";
        String sortFieldParam = req.getParameter(SORT_FIELD_PARAM);
        String sortOrderParam = req.getParameter(SORT_ORDER_PARAM);
        SortField sortField = SortField.DEFAULT;
        SortOrder sortOrder = SortOrder.DEFAULT;
        if ((sortFieldParam != null && !sortFieldParam.isEmpty()) && (sortOrderParam != null && !sortOrderParam.isEmpty())) {
            sortField = SortField.valueOf(sortFieldParam.toUpperCase());
            sortOrder = SortOrder.valueOf(sortOrderParam.toUpperCase());
        }
        req.setAttribute(PRODUCT_LIST_ATTRIBUTE, dao.findProducts(query, sortField, sortOrder));
        req.getRequestDispatcher(PRODUCT_LIST_PATH).forward(req, resp);
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
            if ((req.getParameter(SEARCH_QUERY_PARAM) == null || req.getParameter(SEARCH_QUERY_PARAM).isEmpty())
                    && (req.getParameter(SORT_FIELD_PARAM) == null || req.getParameter(SORT_FIELD_PARAM).isEmpty())
                    && (req.getParameter(SORT_ORDER_PARAM) == null || req.getParameter(SORT_ORDER_PARAM).isEmpty())) {
                resp.sendRedirect(String.format("%s/products?&message=Added to cart successfully", req.getContextPath()));
                return;
            }
            if (req.getParameter(SORT_FIELD_PARAM) == null || req.getParameter(SORT_FIELD_PARAM).isEmpty()) {
                resp.sendRedirect(String.format("%s/products?query=%s&message=Added to cart successfully", req.getContextPath(), req.getParameter(SEARCH_QUERY_PARAM)));
            } else {
                resp.sendRedirect(String.format(REDIRECT_AFTER_ADDING_TO_CART, req.getContextPath(),
                        req.getParameter(SEARCH_QUERY_PARAM),
                        req.getParameter(SORT_FIELD_PARAM), req.getParameter(SORT_ORDER_PARAM), product.get().getId()));
            }
        }else{

        }
    }

    private Optional<Product> attachProductOrSendError(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String productId = req.getParameter("productId");
        try {
            Product product = dao.findProduct(Long.valueOf(productId));
            req.setAttribute(REQUEST_ATTRIBUTE_PRODUCT, product);
            return Optional.of(product);
        } catch (NumberFormatException | ItemNotFoundException e) {
            req.setAttribute(REQUEST_ATTRIBUTE_MESSAGE, String.format(ERROR_NOT_FOUND, productId));
            resp.sendError(NOT_FOUND_ERROR_CODE);
            return Optional.empty();
        }
    }

    private Optional<Integer> extractQuantityOrForward(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            return Optional.of(NumberFormat.getInstance(
                    req.getLocale()).parse(req.getParameter(REQUEST_PARAM_QUANTITY)).intValue());
        } catch (NumberFormatException | ParseException e) {
            req.setAttribute(REQUEST_ATTRIBUTE_ERROR, ERROR_NOT_NUMBER);
            doGet(req, resp);
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
            req.setAttribute(REQUEST_ATTRIBUTE_ERROR, String.format(ERROR_NOT_ENOUGH_STOCK, e.getAvailableAmount()));
            doGet(req, resp);
            return false;
        }
    }
}
