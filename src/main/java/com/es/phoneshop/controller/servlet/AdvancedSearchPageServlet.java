package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.service.AdvancedSearchService;
import com.es.phoneshop.model.service.impl.DefaultAdvancedSearchService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdvancedSearchPageServlet extends HttpServlet {
    private AdvancedSearchService advancedSearchService = DefaultAdvancedSearchService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productCode = req.getParameter("productCode");
        String potentialMinPrice = req.getParameter("minPrice");
        String potentialMaxPrice = req.getParameter("maxPrice");
        String potentialMinStock = req.getParameter("minStock");
        Map<String, String> searchErrors = new HashMap<>();
        Double minPrice = null;
        Double maxPrice = null;
        Integer minStock = null;
        if (potentialMinPrice != null && !potentialMinPrice.isEmpty()) {
            try {
                minPrice = Double.parseDouble(potentialMinPrice);
            } catch (NumberFormatException e) {
                searchErrors.put("minPriceError", "Not a number.");
            }
        }
        if (potentialMaxPrice != null && !potentialMaxPrice.isEmpty()) {
            try {
                maxPrice = Double.parseDouble(potentialMaxPrice);
            } catch (NumberFormatException e) {
                searchErrors.put("maxPriceError", "Not a number.");
            }
        }
        if (potentialMinStock != null && !potentialMinStock.isEmpty()) {
            try {
                minStock = Integer.parseInt(potentialMinStock);
            } catch (NumberFormatException e) {
                searchErrors.put("minStockError", "Not a number.");
            }
        }
        if (searchErrors.isEmpty()) {
            req.setAttribute("products", advancedSearchService
                    .searchAdvancedProducts(productCode, minPrice, maxPrice, minStock));
        } else {
            req.setAttribute("searchErrors", searchErrors);
        }
        req.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(req, resp);
    }
}
