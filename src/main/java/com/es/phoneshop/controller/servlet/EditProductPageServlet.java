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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;

public class EditProductPageServlet extends HttpServlet {
    private static final String ADVANCED_SEARCH_JSP = "WEB-INF/pages/editProduct.jsp";
    private ProductService productService = DefaultProductService.getInstance();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productCode = req.getParameter(Const.RequestParam.SEARCH_TAG);
        String potentialMinPrice = req.getParameter(Const.RequestParam.MIN_PRICE);
        String potentialMaxPrice = req.getParameter(Const.RequestParam.MAX_PRICE);
        String potentialMinStock = req.getParameter(Const.RequestParam.SEARCH_STOCK);
        Map<String, String> errors = new HashMap<>();
        Double minPrice = handleNonNegativeDouble(potentialMinPrice, Const.ErrorKey.MIN_PRICE, errors);
        Double maxPrice = handleNonNegativeDouble(potentialMaxPrice, Const.ErrorKey.MAX_PRICE, errors);
        Integer minStock = handleNonNegativeInt(potentialMinStock, Const.ErrorKey.STOCK, errors);
        if (errors.isEmpty()) {
            req.setAttribute(Const.AttributeKey.PRODUCTS, productService.filterProductsByFields(
                    productService.findAll(), minPrice, maxPrice, minStock, productCode));
        } else {
            req.setAttribute(Const.AttributeKey.SEARCH_ERRORS, errors);
        }
        req.getRequestDispatcher(ADVANCED_SEARCH_JSP).forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] productIds = req.getParameterValues(Const.RequestParam.PRODUCT_ID);
        String[] imageUrls = req.getParameterValues(Const.RequestParam.IMAGE_URL);
        String[] tags = req.getParameterValues(Const.RequestParam.TAG);
        String[] descriptions = req.getParameterValues(Const.RequestParam.DESCRIPTION);
        String[] prices = req.getParameterValues(Const.RequestParam.PRICE);
        String[] stocks = req.getParameterValues(Const.RequestParam.STOCK);
        Map<Long, Map<String, String>> productsErrors = new HashMap<>();
        for (int i = 0; i < productIds.length; i++) {
            long id = Long.parseLong(productIds[i]);
            Map<String, String> errors = new HashMap<>();
            updateString(id, imageUrls[i], errors, Const.ErrorKey.IMAGE_URL, productService::updateImageUrl);
            updateString(id, tags[i], errors, Const.ErrorKey.TAG, productService::updateTag);
            updateString(id, descriptions[i], errors, Const.ErrorKey.DESCRIPTION, productService::updateDescription);
            updateDouble(id, prices[i], errors, Const.ErrorKey.PRICE, productService::updatePrice);
            updateInt(id, stocks[i], errors, Const.ErrorKey.STOCK, productService::updateStock);
            if (!errors.isEmpty()) {
                productsErrors.put(id, errors);
            }
        }
        if (!productsErrors.isEmpty()) {
            req.setAttribute(Const.AttributeKey.ERRORS, productsErrors);
            doGet(req, resp);
        }else{
            resp.sendRedirect(req.getContextPath() + "/editProduct");
        }
    }

    public Double handleNonNegativeDouble(String potentialDouble, String errorKey, Map<String, String> errors) {
        if (potentialDouble == null || potentialDouble.isEmpty()) {
            return null;
        }
        Optional<Double> number = CustomParser.parseNonNegativeDouble(potentialDouble);
        if (number.isEmpty()) {
            errors.put(errorKey, Const.ErrorInfo.NON_NEGATIVE_NUMBER);
        }
        return number.orElse(null);
    }

    public Integer handleNonNegativeInt(String potentialInteger, String errorKey, Map<String, String> errors) {
        if (potentialInteger == null || potentialInteger.isEmpty()) {
            return null;
        }
        Optional<Integer> number = CustomParser.parseNonNegativeInt(potentialInteger);
        if (number.isEmpty()) {
            errors.put(errorKey, Const.ErrorInfo.NON_NEGATIVE_INT);
        }
        return number.orElse(null);
    }

    private void updateString(long productId, String imageUrl, Map<String, String> error, String errorKey,
                              BiConsumer<Long, String> consumer) {
        if (imageUrl.isEmpty()) {
            error.put(errorKey, Const.ErrorInfo.VALUE_IS_REQUIRED);
        } else {
            consumer.accept(productId, imageUrl);
        }
    }

    private void updateDouble(long productId, String potentialDouble, Map<String, String> error, String errorKey,
                              ObjDoubleConsumer<Long> consumer) {
        CustomParser.parseNonNegativeDouble(potentialDouble)
                .ifPresentOrElse(d -> consumer.accept(productId, d),
                        () -> error.put(errorKey, Const.ErrorInfo.NON_NEGATIVE_NUMBER));
    }

    private void updateInt(long productId, String potentialInt, Map<String, String> error, String errorKey,
                           ObjIntConsumer<Long> consumer) {
        CustomParser.parseNonNegativeInt(potentialInt)
                .ifPresentOrElse(i -> consumer.accept(productId, i),
                        () -> error.put(errorKey, Const.ErrorInfo.NON_NEGATIVE_INT));
    }
}
