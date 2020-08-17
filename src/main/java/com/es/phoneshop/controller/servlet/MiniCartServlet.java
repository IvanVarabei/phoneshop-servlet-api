package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MiniCartServlet extends HttpServlet {
    private static final String MINI_CART_JSP = "/WEB-INF/pages/miniCart.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(Const.RequestAttribute.CART, req.getSession().getAttribute(Const.RequestAttribute.CART));
        req.getRequestDispatcher(MINI_CART_JSP).include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
