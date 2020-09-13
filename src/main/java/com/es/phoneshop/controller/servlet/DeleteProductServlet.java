package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productId = Long.parseLong(req.getPathInfo().substring(1));
        productDao.delete(productId);
        resp.sendRedirect(req.getContextPath() + "/editProduct?" + req.getQueryString());
    }
}
