package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.impl.ArrayListUserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginPageServlet extends HttpServlet {
    private ArrayListUserDao dao = ArrayListUserDao.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        if(dao.ifExist(login, password)){
            session.setAttribute("login", login);
            session.setAttribute("password", password);
            String s1 = req.getContextPath();
            String s2 = session.getAttribute("destination").toString();
            resp.sendRedirect(req.getContextPath() + session.getAttribute("destination"));
        }else{
            req.setAttribute("message", "There is no user having such login and password.");
            req.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(req, resp);
        }
    }
}
