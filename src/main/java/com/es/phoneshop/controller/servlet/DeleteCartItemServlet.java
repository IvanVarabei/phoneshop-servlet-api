package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.model.service.ProductService;
import com.es.phoneshop.model.service.impl.DefaultCartService;
import com.es.phoneshop.model.service.impl.DefaultProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {
    private static final String REDIRECT_AFTER_DELETING_FROM_CART = "%s/cart?message=Cart item removed successfully";
    private CartService cartService = DefaultCartService.getInstance();
    private ProductService productService = DefaultProductService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            cartService.delete(req.getSession(), productService.find(Long.parseLong(req.getPathInfo().substring(1))));
            resp.sendRedirect(String.format(REDIRECT_AFTER_DELETING_FROM_CART, req.getContextPath()));
        } catch (ItemNotFoundException | NumberFormatException e) {
            e.printStackTrace();//todo
        }
    }
}
