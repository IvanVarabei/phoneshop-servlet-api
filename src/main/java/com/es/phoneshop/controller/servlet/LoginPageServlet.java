package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.service.UserService;
import com.es.phoneshop.model.service.impl.DefaultUserService;
import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginPageServlet extends HttpServlet {
    private static final String LOGIN_JSP = "WEB-INF/pages/login.jsp";
    private UserService userService = DefaultUserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LOGIN_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(Const.AttributeKey.LOGIN);
        String password = req.getParameter(Const.AttributeKey.PASSWORD);
        HttpSession session = req.getSession();
        if (userService.ifExist(login, password)) {
            session.setAttribute(Const.AttributeKey.LOGIN, login);
            session.setAttribute(Const.AttributeKey.PASSWORD, password);
            resp.sendRedirect(req.getContextPath() + session.getAttribute(Const.AttributeKey.DESIRABLE_SECURED_PAGE));
        } else {
            req.setAttribute(Const.AttributeKey.ERROR, Const.ErrorInfo.WRONG_LOGIN_PASSWORD);
            req.getRequestDispatcher(LOGIN_JSP).forward(req, resp);
        }
    }
}
