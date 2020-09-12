package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.service.AdvancedSearchService;
import com.es.phoneshop.model.service.impl.DefaultAdvancedSearchService;
import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdvancedSearchPageServlet extends HttpServlet {
    private static final String ADVANCED_SEARCH_JSP = "/WEB-INF/pages/advancedSearch.jsp";
    private AdvancedSearchService advancedSearchService = DefaultAdvancedSearchService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productCode = req.getParameter(Const.RequestParam.PRODUCT_CODE);
        String potentialMinPrice = req.getParameter(Const.RequestParam.MIN_PRICE);
        String potentialMaxPrice = req.getParameter(Const.RequestParam.MAX_PRICE);
        String potentialMinStock = req.getParameter(Const.RequestParam.MIN_STOCK);
        Map<String, String> searchErrors = new HashMap<>();
        Double minPrice = extractDouble(potentialMinPrice, Const.ErrorInfo.MIN_PRICE_ERROR, searchErrors);
        Double maxPrice = extractDouble(potentialMaxPrice, Const.ErrorInfo.MAX_PRICE_ERROR, searchErrors);
        Integer minStock = extractInteger(potentialMinStock, Const.ErrorInfo.MIN_STOCK_ERROR, searchErrors);
        if (searchErrors.isEmpty()) {
            req.setAttribute(Const.RequestAttribute.PRODUCTS, advancedSearchService
                    .searchAdvancedProducts(productCode, minPrice, maxPrice, minStock));
        } else {
            req.setAttribute(Const.RequestAttribute.SEARCH_ERRORS, searchErrors);
        }
        req.getRequestDispatcher(ADVANCED_SEARCH_JSP).forward(req, resp);
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
}
