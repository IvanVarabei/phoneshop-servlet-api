package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.entity.User;
import com.es.phoneshop.model.service.UserService;
import com.es.phoneshop.model.service.impl.DefaultUserService;
import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterPageServlet extends HttpServlet {
    private static final String REGISTER_JSP = "WEB-INF/pages/register.jsp";
    private static final String REDIRECT_AFTER_REGISTER = "index.jsp";
    private UserService userService = DefaultUserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(REGISTER_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(Const.AttributeKey.LOGIN);
        String password = req.getParameter(Const.AttributeKey.PASSWORD);
        String repeatPassword = req.getParameter(Const.AttributeKey.REPEAT_PASSWORD);
        if (!password.equals(repeatPassword)) {
            req.setAttribute(Const.AttributeKey.ERROR, Const.ErrorInfo.DIFFERENT_PASSWORDS);
            req.getRequestDispatcher(REGISTER_JSP).forward(req, resp);
        } else {
            if (userService.ifLoginExist(login)) {
                req.setAttribute(Const.AttributeKey.ERROR, Const.ErrorInfo.USER_ALREADY_EXIST);
                req.getRequestDispatcher(REGISTER_JSP).forward(req, resp);
            } else {
                userService.save(new User(login, password, User.Role.CLIENT));
                resp.sendRedirect(REDIRECT_AFTER_REGISTER);
            }
        }
    }
}
