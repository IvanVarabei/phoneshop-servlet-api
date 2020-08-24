package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RecentlyViewedServlet extends HttpServlet {
    private static final String RECENTLY_VIEWED_JSP = "/WEB-INF/pages/recentlyViewed.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(Const.RequestAttribute.RECENT, req.getSession().getAttribute(Const.RequestAttribute.RECENT));
        req.getRequestDispatcher(RECENTLY_VIEWED_JSP).include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
