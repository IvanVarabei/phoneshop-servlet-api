package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.model.service.ProductService;
import com.es.phoneshop.model.service.impl.DefaultCartService;
import com.es.phoneshop.model.service.impl.DefaultProductService;
import com.es.phoneshop.util.CustomParser;
import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;

public class ProductListPageServlet extends HttpServlet {
    private static final String PRODUCT_LIST_JSP = "WEB-INF/pages/productList.jsp";
    private static final String REDIRECT_AFTER_ADDING_TO_CART = "%s%s?%s&message=Added to cart successfully";
    private ProductService productService = DefaultProductService.getInstance();
    private CartService cartService = DefaultCartService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = Objects.toString(req.getParameter(Const.RequestParam.SEARCH_QUERY), "");
        String sortFieldParam = req.getParameter(Const.RequestParam.SORT_FIELD);
        String sortOrderParam = req.getParameter(Const.RequestParam.SORT_ORDER);
        SortField sortField = SortField.DEFAULT;
        SortOrder sortOrder = SortOrder.DEFAULT;
        if ((sortFieldParam != null && !sortFieldParam.isEmpty())
                && (sortOrderParam != null && !sortOrderParam.isEmpty())) {
            sortField = SortField.valueOf(sortFieldParam.toUpperCase());
            sortOrder = SortOrder.valueOf(sortOrderParam.toUpperCase());
        }
        req.setAttribute(Const.AttributeKey.TAGS, productService.findTags());
        List<Product> requestedSortedProducts = productService.findProducts(query, sortField, sortOrder);
        req.setAttribute(Const.AttributeKey.PRODUCTS, filterProductsByRequest(req, requestedSortedProducts));
        req.getRequestDispatcher(PRODUCT_LIST_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Product> product = findProductOrSendError(req, resp);
        if (product.isEmpty()) {
            return;
        }
        Optional<Integer> quantity = extractQuantityOrForward(req, resp);
        if (quantity.isEmpty()) {
            return;
        }
        try {
            cartService.add(req.getSession(), product.get(), quantity.get());
            resp.sendRedirect(String.format(REDIRECT_AFTER_ADDING_TO_CART, req.getContextPath(),
                    req.getServletPath(), req.getQueryString()));
        } catch (OutOfStockException e) {
            req.setAttribute(Const.AttributeKey.ERROR,
                    String.format(Const.ErrorInfo.NOT_ENOUGH_STOCK, e.getAvailableAmount()));
            doGet(req, resp);
        }
    }

    private List<Product> filterProductsByRequest(HttpServletRequest req, List<Product> items) {
        String[] productCods = req.getParameterValues(Const.RequestParam.TAG);
        String potentialMinPrice = req.getParameter(Const.RequestParam.MIN_PRICE);
        String potentialMaxPrice = req.getParameter(Const.RequestParam.MAX_PRICE);
        String potentialMinStock = req.getParameter(Const.RequestParam.STOCK);
        Map<String, Boolean> errors = new HashMap<>();
        Double minPrice = extractValueOrSetError(potentialMinPrice, errors, Const.ErrorKey.MIN_PRICE,
                CustomParser::parseNonNegativeDouble);
        Double maxPrice = extractValueOrSetError(potentialMaxPrice, errors, Const.ErrorKey.MAX_PRICE,
                CustomParser::parseNonNegativeDouble);
        Integer minStock = extractValueOrSetError(potentialMinStock, errors, Const.ErrorKey.STOCK,
                CustomParser::parseNonNegativeInt);
        if (!errors.isEmpty()) {
            req.setAttribute(Const.AttributeKey.ERRORS, errors);
        }
        return productService.filterProductsByFields(items, minPrice, maxPrice, minStock, productCods);
    }

    private <T> T extractValueOrSetError(String potentialMinPrice, Map<String, Boolean> errors,
                                         String errorKey, Function<String, Optional<T>> function) {
        return function.apply(potentialMinPrice).orElseGet(() -> {
            errors.put(errorKey, true);
            return null;//todo
        });
    }

    private Optional<Product> findProductOrSendError(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String productId = req.getParameter(Const.RequestParam.PRODUCT_ID);
        try {
            Product product = productService.find(Long.parseLong(productId));
            return Optional.of(product);
        } catch (NumberFormatException | ItemNotFoundException e) {
            req.setAttribute(Const.AttributeKey.MESSAGE, String.format(Const.ErrorInfo.PRODUCT_NOT_FOUND, productId));
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
            req.setAttribute(Const.AttributeKey.ERROR, Const.ErrorInfo.NOT_NUMBER);
            doGet(req, resp);
            return Optional.empty();
        }
    }
}
