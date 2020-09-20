package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.service.ProductService;
import com.es.phoneshop.model.service.impl.DefaultProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {
    private static final String REDIRECT_AFTER_DELETING = "%s/editProduct?%s";
    private ProductService productService = DefaultProductService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productId = Long.parseLong(req.getPathInfo().substring(1));
        productService.delete(productId);
        resp.sendRedirect(String.format(REDIRECT_AFTER_DELETING, req.getContextPath(), req.getQueryString()));
    }
}
