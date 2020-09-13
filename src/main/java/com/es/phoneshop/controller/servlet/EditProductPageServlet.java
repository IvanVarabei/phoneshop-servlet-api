package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.service.ProductService;
import com.es.phoneshop.model.service.impl.DefaultProductService;
import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProductPageServlet extends HttpServlet {
    private static final String ADVANCED_SEARCH_JSP = "/WEB-INF/pages/editProduct.jsp";
    private ProductService productService = DefaultProductService.getInstance();
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();
    Pattern intNotNegativePattern = Pattern.compile("^\\d+$");
    Pattern notNegativePattern = Pattern.compile("^\\d+[.,]?\\d*$");

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
            req.setAttribute(Const.RequestAttribute.PRODUCTS, productService
                    .searchAdvancedProducts(productCode, minPrice, maxPrice, minStock));
        } else {
            req.setAttribute(Const.RequestAttribute.SEARCH_ERRORS, searchErrors);
        }
        req.getRequestDispatcher(ADVANCED_SEARCH_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] productIds = req.getParameterValues(Const.RequestParam.PRODUCT_ID);
        String[] imageUrls = req.getParameterValues("imageUrl");
        String[] codes = req.getParameterValues("code");
        String[] descriptions = req.getParameterValues("description");
        String[] prices = req.getParameterValues("price");
        String[] stocks = req.getParameterValues("stock");
        Map<Long, Map<String,String>> editError = new HashMap<>();
        for (int i = 0; i < productIds.length; i++) {
            long productId = Long.parseLong(productIds[i]);
            Map<String,String> currentProductError = new HashMap<>();
            updateProductImageUrl(productId, imageUrls[i], currentProductError);
            updateProductCode(productId, codes[i], currentProductError);
            updateProductDescription(productId, descriptions[i], currentProductError);
            updateProductPrice(productId, prices[i], currentProductError);
            updateProductStock(productId, stocks[i], currentProductError);
            if(!currentProductError.isEmpty()) {
                editError.put(productId, currentProductError);
            }
        }
        if(!editError.isEmpty()) {
            req.setAttribute("editError", editError);
        }
        doGet(req, resp);
    }

    private void updateProductImageUrl(long productId, String imageUrl, Map<String,String> currentProductError) {
        if (imageUrl.isEmpty()) {
            currentProductError.put("imageUrlError", "can not be empty");
        } else {
            productDao.updateProductImageUrl(productId, imageUrl);
        }
    }

    private void updateProductCode(long productId, String code, Map<String,String> currentProductError) {
        if (code.isEmpty()) {
            currentProductError.put("codeError", "can not be empty");
        } else {
            productDao.updateProductCode(productId, code);
        }
    }

    private void updateProductDescription(long productId, String description, Map<String,String> currentProductError) {
        if (description.isEmpty()) {
            currentProductError.put("descriptionError", "can not be empty");
        } else {
            productDao.updateProductDescription(productId, description);
        }
    }

    private void updateProductPrice(long productId, String potentialPrice, Map<String,String> currentProductError) {
        Matcher matcher = notNegativePattern.matcher(potentialPrice);
        if (matcher.find()) {
            productDao.updateProductPrice(productId, Double.parseDouble(matcher.group()));
        } else {
            currentProductError.put("priceError", "must be non negative");
        }
    }

    private void updateProductStock(long productId, String potentialStock, Map<String,String> currentProductError) {
        Matcher matcher = intNotNegativePattern.matcher(potentialStock);
        if (matcher.find()) {
            productDao.updateProductStock(productId, Integer.parseInt(matcher.group()));
        } else {
            currentProductError.put("stockError", "must be non negative int");
        }
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
