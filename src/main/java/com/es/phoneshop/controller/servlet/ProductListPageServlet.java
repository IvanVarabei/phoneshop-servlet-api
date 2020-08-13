package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.controller.value.Const;
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
    private static final String PRODUCT_LIST_JSP = "/WEB-INF/pages/productList.jsp";
    private static final String REDIRECT_AFTER_ADDING_TO_CART =
            "%s/products?query=%s&sortField=%s&sortOrder=%s&message=Added to cart successfully";
    private ArrayListProductDao dao = ArrayListProductDao.getInstance();
    private CartService cartService = CartService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter(Const.RequestParam.SEARCH_QUERY) != null ?
                req.getParameter(Const.RequestParam.SEARCH_QUERY) : "";
        String sortFieldParam = req.getParameter(Const.RequestParam.SORT_FIELD);
        String sortOrderParam = req.getParameter(Const.RequestParam.SORT_ORDER);
        SortField sortField = SortField.DEFAULT;
        SortOrder sortOrder = SortOrder.DEFAULT;
        if ((sortFieldParam != null && !sortFieldParam.isEmpty())
                && (sortOrderParam != null && !sortOrderParam.isEmpty())) {
            sortField = SortField.valueOf(sortFieldParam.toUpperCase());
            sortOrder = SortOrder.valueOf(sortOrderParam.toUpperCase());
        }
        req.setAttribute(Const.RequestAttribute.PRODUCTS, dao.findProducts(query, sortField, sortOrder));
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
        if (addProductToCartOrForward(req, resp, product.get(), quantity.get())) {
            resp.sendRedirect(String.format(REDIRECT_AFTER_ADDING_TO_CART, req.getContextPath(),
                    req.getParameter(Const.RequestParam.SEARCH_QUERY), req.getParameter(Const.RequestParam.SORT_FIELD),
                    req.getParameter(Const.RequestParam.SORT_ORDER)));
        }
    }

    private Optional<Product> findProductOrSendError(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String productId = req.getParameter(Const.RequestParam.PRODUCT_ID);
        try {
            Product product = dao.findProduct(Long.valueOf(productId));
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
