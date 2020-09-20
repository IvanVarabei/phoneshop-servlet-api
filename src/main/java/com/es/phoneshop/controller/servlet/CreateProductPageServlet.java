package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.service.ProductService;
import com.es.phoneshop.model.service.impl.DefaultProductService;
import com.es.phoneshop.util.CustomParser;
import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CreateProductPageServlet extends HttpServlet {
    private static final String CREATE_PRODUCT_JSP = "WEB-INF/pages/createProduct.jsp";
    private static final String REDIRECT_AFTER_CREATING = "%s%s?message=Product created successfully";
    private ProductService productService = DefaultProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(CREATE_PRODUCT_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String potentialImageUrl = req.getParameter(Const.RequestParam.IMAGE_URL);
        String potentialProductCode = req.getParameter(Const.RequestParam.TAG);
        String potentialDescription = req.getParameter(Const.RequestParam.DESCRIPTION);
        String potentialPrice = req.getParameter(Const.RequestParam.PRICE);
        String potentialStock = req.getParameter(Const.RequestParam.STOCK);
        Map<String, String> errors = new HashMap<>();
        String imageUrl = extractStringOrSetError(potentialImageUrl, Const.ErrorKey.IMAGE_URL, errors);
        String productCode = extractStringOrSetError(potentialProductCode, Const.ErrorKey.TAG, errors);
        String description = extractStringOrSetError(potentialDescription, Const.ErrorKey.DESCRIPTION, errors);
        Double price = extractNonNegativeDoubleOrSetError(potentialPrice, Const.ErrorKey.PRICE, errors);
        Integer stock = extractNonNegativeIntOrSetError(potentialStock, Const.ErrorKey.STOCK, errors);
        if (!errors.isEmpty()) {
            req.setAttribute(Const.AttributeKey.ERRORS, errors);
            req.getRequestDispatcher(CREATE_PRODUCT_JSP).forward(req, resp);
        } else {
            productService.saveProduct(imageUrl, productCode, description, BigDecimal.valueOf(price), stock);
            resp.sendRedirect(String.format(REDIRECT_AFTER_CREATING, req.getContextPath(), req.getServletPath()));
        }
    }

    private String extractStringOrSetError(String sourceData, String errorKey, Map<String, String> errors) {
        if (sourceData != null && !sourceData.isEmpty()) {
            return sourceData;
        } else {
            errors.put(errorKey, Const.ErrorInfo.VALUE_IS_REQUIRED);
            return null; //todo
        }
    }

    private Double extractNonNegativeDoubleOrSetError(String sourceData, String errorKey, Map<String, String> errors) {
        Optional<Double> number = CustomParser.parseNonNegativeDouble(sourceData);
        if (number.isEmpty()) {
            errors.put(errorKey, Const.ErrorInfo.NON_NEGATIVE_NUMBER);
        }
        return number.orElse(null);
    }

    private Integer extractNonNegativeIntOrSetError(String sourceData, String errorKey, Map<String, String> errors) {
        Optional<Integer> number = CustomParser.parseNonNegativeInt(sourceData);
        if (number.isEmpty()) {
            errors.put(errorKey, Const.ErrorInfo.NON_NEGATIVE_INT);
        }
        return number.orElse(null);
    }
}
