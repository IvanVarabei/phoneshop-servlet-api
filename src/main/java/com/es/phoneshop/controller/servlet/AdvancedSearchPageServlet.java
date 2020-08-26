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
        Double minPrice = null;
        Double maxPrice = null;
        Integer minStock = null;
        if (potentialMinPrice != null && !potentialMinPrice.isEmpty()) {
            try {
                minPrice = Double.parseDouble(potentialMinPrice);
            } catch (NumberFormatException e) {
                searchErrors.put("minPriceError", Const.ErrorInfo.NOT_NUMBER);
            }
        }
        if (potentialMaxPrice != null && !potentialMaxPrice.isEmpty()) {
            try {
                maxPrice = Double.parseDouble(potentialMaxPrice);
            } catch (NumberFormatException e) {
                searchErrors.put("maxPriceError", Const.ErrorInfo.NOT_NUMBER);
            }
        }
        if (potentialMinStock != null && !potentialMinStock.isEmpty()) {
            try {
                minStock = Integer.parseInt(potentialMinStock);
            } catch (NumberFormatException e) {
                searchErrors.put("minStockError", Const.ErrorInfo.NOT_NUMBER);
            }
        }
        if (searchErrors.isEmpty()) {
            req.setAttribute(Const.RequestAttribute.PRODUCTS, advancedSearchService
                    .searchAdvancedProducts(productCode, minPrice, maxPrice, minStock));
        } else {
            req.setAttribute(Const.RequestAttribute.SEARCH_ERRORS, searchErrors);
        }
        req.getRequestDispatcher(ADVANCED_SEARCH_JSP).forward(req, resp);
    }
}
