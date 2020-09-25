package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.value.Const;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    private static final String REDIRECT_AFTER_LOGOUT = "index.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.removeAttribute(Const.AttributeKey.LOGIN);
        session.removeAttribute(Const.AttributeKey.PASSWORD);
        session.removeAttribute(Const.AttributeKey.DESIRABLE_SECURED_PAGE);

        resp.sendRedirect(REDIRECT_AFTER_LOGOUT);
    }
}
