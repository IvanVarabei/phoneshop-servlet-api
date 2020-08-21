package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.exception.OrderNotFoundException;
import com.es.phoneshop.model.service.OrderService;
import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
    private static final String ORDER_OVERVIEW_JSP = "/WEB-INF/pages/orderOverview.jsp";
    private OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String secureId = req.getPathInfo().substring(1);
        try {
            req.setAttribute("order", orderService.findOrderBySecureId(secureId));
            req.getRequestDispatcher(ORDER_OVERVIEW_JSP).forward(req, resp);
        } catch (OrderNotFoundException e) {
            req.setAttribute(Const.RequestAttribute.MESSAGE, String.format(Const.ErrorInfo.ORDER_NOT_FOUND, secureId));
            resp.sendError(Const.ErrorInfo.PAGE_NOT_FOUND_CODE);
        }
    }
}
