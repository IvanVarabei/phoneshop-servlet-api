package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.exception.ItemNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private static final String PRODUCT_CODE_ATTRIBUTE = "product";
    private static final String ERROR_MESSAGE_ATTRIBUTE = "message";
    private static final String PRODUCT_DETAILS_PATH = "/WEB-INF/pages/productDetails.jsp";
    private static final String ERROR_MESSAGE_PART = "Product with code '%s' not found.";
    private static final int NOT_FOUND_ERROR_CODE = 404;
    private ArrayListProductDao arrayListProductDao = ArrayListProductDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getPathInfo().substring(1);
        try {
            request.setAttribute(PRODUCT_CODE_ATTRIBUTE, arrayListProductDao.findProduct(Long.valueOf(productId)));
            request.getRequestDispatcher(PRODUCT_DETAILS_PATH).forward(request, response);
        } catch (NumberFormatException | ItemNotFoundException e) {
            request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, String.format(ERROR_MESSAGE_PART, productId));
            response.sendError(NOT_FOUND_ERROR_CODE);
        }
    }
}
