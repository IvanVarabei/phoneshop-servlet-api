package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.service.CartService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {
    private static final String REDIRECT_AFTER_DELETING_FROM_CART = "/cart?message=Cart item removed successfully";
    private CartService cartService = CartService.getInstance();
    private ProductDao dao = ArrayListProductDao.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            cartService.delete(req.getSession(), dao.find(Long.valueOf(req.getPathInfo().substring(1))));
            resp.sendRedirect(req.getContextPath() + REDIRECT_AFTER_DELETING_FROM_CART);
        } catch (ItemNotFoundException | NumberFormatException e) {
            log("Unexpected : ItemNotFoundException | NumberFormatException");
        }
    }
}
