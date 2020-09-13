package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.service.ProductService;
import com.es.phoneshop.model.service.impl.DefaultProductService;
import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CreateProductServlet extends HttpServlet {
    private ProductService productService = DefaultProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("WEB-INF/pages/createProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String imageUrl = req.getParameter("imageUrl");
        String productCode = req.getParameter(Const.RequestParam.PRODUCT_CODE);
        String description = req.getParameter("description");
        String potentialPrice = req.getParameter("price");
        String potentialStock = req.getParameter("stock");
        Map<String, String> errors = new HashMap<>();
        Double price = extractDouble(potentialPrice, "priceError", errors);
        Integer stock = extractInteger(potentialStock, "stockError", errors);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
        } else {
            productService.saveProduct(imageUrl, productCode, description, BigDecimal.valueOf(price), stock);
        }
        req.getRequestDispatcher("WEB-INF/pages/createProduct.jsp").forward(req, resp);
    }

    private Double extractDouble(String potentialDouble, String errorName, Map<String, String> searchErrors) {
        if (potentialDouble != null) {
            try {
                return Double.parseDouble(potentialDouble);
            } catch (NumberFormatException e) {
                searchErrors.put(errorName, Const.ErrorInfo.NOT_NUMBER);
            }
        }
        return null;
    }

    private Integer extractInteger(String potentialInteger, String errorName, Map<String, String> searchErrors) {
        if (potentialInteger != null) {
            try {
                return Integer.parseInt(potentialInteger);
            } catch (NumberFormatException e) {
                searchErrors.put(errorName, Const.ErrorInfo.NOT_NUMBER);
            }
        }
        return null;
    }
}
