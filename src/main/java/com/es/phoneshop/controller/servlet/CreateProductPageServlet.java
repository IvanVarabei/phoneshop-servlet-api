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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateProductPageServlet extends HttpServlet {
    private ProductService productService = DefaultProductService.getInstance();
    Pattern intNotNegativePattern = Pattern.compile("^\\d+$");
    Pattern notNegativePattern = Pattern.compile("^\\d+[.,]?\\d*$");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("WEB-INF/pages/createProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String potentialImageUrl = req.getParameter("imageUrl");
        String potentialProductCode = req.getParameter(Const.RequestParam.PRODUCT_CODE);
        String potentialDescription = req.getParameter("description");
        String potentialPrice = req.getParameter("price");
        String potentialStock = req.getParameter("stock");
        Map<String, String> errors = new HashMap<>();
        String imageUrl = handlePotentialString(potentialImageUrl, "imageUrlError", errors);
        String productCode = handlePotentialString(potentialProductCode, "productCodeError", errors);
        String description = handlePotentialString(potentialDescription, "descriptionError", errors);
        Double price = extractDouble(potentialPrice, "priceError", errors);
        Integer stock = extractInteger(potentialStock, "stockError", errors);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("WEB-INF/pages/createProduct.jsp").forward(req, resp);
        } else {
            productService.saveProduct(imageUrl, productCode, description, BigDecimal.valueOf(price), stock);
            resp.sendRedirect(req.getContextPath() + req.getServletPath());
        }
    }

    private String handlePotentialString(String potentialString, String errorName, Map<String, String> searchErrors) {
        if (potentialString != null && !potentialString.isEmpty()) {
            return potentialString;
        } else {
            searchErrors.put(errorName, "can not be empty");
            return null;
        }
    }

    private Double extractDouble(String potentialDouble, String errorName, Map<String, String> searchErrors) {
        Matcher matcher = notNegativePattern.matcher(potentialDouble);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group());
        }
        searchErrors.put("priceError", "must be non negative");
        return null;
    }

    private Integer extractInteger(String potentialInteger, String errorName, Map<String, String> searchErrors) {
        Matcher matcher = intNotNegativePattern.matcher(potentialInteger);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
            searchErrors.put("stockError", "must be non negative int");
        return null;
    }
}
