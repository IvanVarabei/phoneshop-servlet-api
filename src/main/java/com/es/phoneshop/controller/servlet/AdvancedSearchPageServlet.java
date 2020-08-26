package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancedSearchPageServlet extends HttpServlet {
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            req.setAttribute("products", searchProducts(productCode, minPrice, maxPrice, minStock));
        } else {
            req.setAttribute("searchErrors", searchErrors);
        }
        req.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(req, resp);
    }

    private List<Product> searchProducts(String productCode, Double minPrice, Double maxPrice, Integer minStock) {
        if (productCode == null || productCode.isEmpty() && minPrice == null && maxPrice == null && minStock == null) {
            return productDao.findProducts("", SortField.DEFAULT, SortOrder.DEFAULT);
        }
        if (productCode != null && !productCode.isEmpty() && minPrice == null && maxPrice == null && minStock == null) {
            return productDao.findByCode(productCode);
        }
        if (productCode == null || productCode.isEmpty() && minPrice != null && maxPrice == null && minStock == null) {
            return productDao.findByMinPrice(minPrice);
        }
        if (productCode == null || productCode.isEmpty() && minPrice == null && maxPrice != null && minStock == null) {
            return productDao.findByMaxPrice(minPrice);
        }
        if (productCode == null || productCode.isEmpty() && minPrice != null && maxPrice != null && minStock == null) {
            return productDao.findByPrice(minPrice, maxPrice);
        }
        if (productCode == null || productCode.isEmpty() && minPrice == null && maxPrice == null && minStock != null) {
            return productDao.findByMinStock(minStock);
        }
        if (productCode != null && !productCode.isEmpty() && minPrice != null && maxPrice != null && minStock != null) {
            return productDao.findByCodePriceMinStock(productCode, minPrice, maxPrice, minStock);
        }
        if (productCode != null && !productCode.isEmpty() && minPrice != null && maxPrice == null && minStock == null) {
            return productDao.findByCodeMinPrice(productCode, minPrice);
        }
        if (productCode != null && !productCode.isEmpty() && minPrice == null && maxPrice != null && minStock == null) {
            return productDao.findByCodeMaxPrice(productCode, maxPrice);
        }
        if (productCode != null && !productCode.isEmpty() && minPrice != null && maxPrice != null && minStock == null) {
            return productDao.findByCodePrice(productCode, minPrice, maxPrice);
        }
        if (productCode != null && !productCode.isEmpty() && minPrice == null && maxPrice == null && minStock != null) {
            return productDao.findByCodeMinStock(productCode, minStock);
        }
        if (productCode == null || productCode.isEmpty() && minPrice == null && maxPrice != null && minStock != null) {
            return productDao.findByMaxPriceMinStock(maxPrice, minStock);
        }
        if (productCode == null || productCode.isEmpty() && minPrice != null && maxPrice == null && minStock != null) {
            return productDao.findByMinPriceMinStock(minPrice, minStock);
        }
        if (productCode == null || productCode.isEmpty() && minPrice != null && maxPrice != null && minStock != null) {
            return productDao.findByPriceMinStock(minPrice, maxPrice, minStock);
        }
        return null;
    }
}
