package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.entity.Product;
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
import java.net.http.HttpRequest;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public class ProductListPageServlet extends HttpServlet {
    private static final String PRODUCT_LIST_JSP = "/WEB-INF/pages/productList.jsp";
    private static final String REDIRECT_AFTER_ADDING_TO_CART =
            "%s/products?query=%s&sortField=%s&sortOrder=%s" +
                    "&minPrice=%s&maxPrice=%s&minStock=%s&message=Added to cart successfully";
    private ProductDao productDao = ArrayListProductDao.getInstance();
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
        List<String> s = productDao.getCategories();
        req.setAttribute("categories", productDao.getCategories());
        req.setAttribute(Const.RequestAttribute.PRODUCTS, getProd(req,productDao.findProducts(query, sortField, sortOrder)));
        req.getRequestDispatcher(PRODUCT_LIST_JSP).forward(req, resp);
    }
//////////////////////////

    protected List<Product> getProd(HttpServletRequest req, List<Product> items) throws ServletException, IOException {
        String[] productCods = req.getParameterValues(Const.RequestParam.PRODUCT_CODE);
        String potentialMinPrice = req.getParameter(Const.RequestParam.MIN_PRICE);
        String potentialMaxPrice = req.getParameter(Const.RequestParam.MAX_PRICE);
        String potentialMinStock = req.getParameter(Const.RequestParam.MIN_STOCK);
        Map<String, String> searchErrors = new HashMap<>();
        Double minPrice = extractDouble(potentialMinPrice, Const.ErrorInfo.MIN_PRICE_ERROR, searchErrors);
        Double maxPrice = extractDouble(potentialMaxPrice, Const.ErrorInfo.MAX_PRICE_ERROR, searchErrors);
        Integer minStock = extractInteger(potentialMinStock, Const.ErrorInfo.MIN_STOCK_ERROR, searchErrors);
        if (searchErrors.isEmpty()) {
            return productService.findByFields(productCods,minPrice,maxPrice,minStock,items);
        } else {
            req.setAttribute(Const.RequestAttribute.SEARCH_ERRORS, searchErrors);
            return items;
        }
    }

    private Double extractDouble(String potentialDouble, String errorName, Map<String, String> searchErrors) {
        if (potentialDouble != null && !potentialDouble.isEmpty()) {
            try {
                return Double.parseDouble(potentialDouble);
            } catch (NumberFormatException e) {
                searchErrors.put(errorName, Const.ErrorInfo.NOT_NUMBER);
            }
        }
        return null;
    }

    private Integer extractInteger(String potentialInteger, String errorName, Map<String, String> searchErrors) {
        if (potentialInteger != null && !potentialInteger.isEmpty()) {
            try {
                return Integer.parseInt(potentialInteger);
            } catch (NumberFormatException e) {
                searchErrors.put(errorName, Const.ErrorInfo.NOT_NUMBER);
            }
        }
        return null;
    }
    //////////////////////////////////////////

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
        if (addProductToCartOrForward(req, resp, product.get(), quantity.get())) {
            resp.sendRedirect(String.format(REDIRECT_AFTER_ADDING_TO_CART + construct(req), req.getContextPath(),
                    req.getParameter(Const.RequestParam.SEARCH_QUERY), req.getParameter(Const.RequestParam.SORT_FIELD),
                    req.getParameter(Const.RequestParam.SORT_ORDER), req.getParameter("minPrice"),
            req.getParameter("maxPrice"), req.getParameter("minStock")));
        }
    }

    private String construct(HttpServletRequest req){
        String s = "";
        String [] productCods = req.getParameterValues("productCode");
        for(int i = 0; i<productCods.length; i++){
            s+="&productCode=" + productCods[i];
        }
        return s;
    }

    private Optional<Product> findProductOrSendError(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String productId = req.getParameter(Const.RequestParam.PRODUCT_ID);
        try {
            Product product = productDao.find(Long.valueOf(productId));
            return Optional.of(product);
        } catch (NumberFormatException | ItemNotFoundException e) {
            req.setAttribute(Const.RequestAttribute.MESSAGE, String.format(Const.ErrorInfo.PRODUCT_NOT_FOUND, productId));
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
            req.setAttribute(Const.RequestAttribute.ERROR,
                    String.format(Const.ErrorInfo.NOT_ENOUGH_STOCK, e.getAvailableAmount()));
            doGet(req, resp);
            return false;
        }
    }
}
