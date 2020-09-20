package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.impl.ArrayListUserDao;
import com.es.phoneshop.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterPageServlet extends HttpServlet {
    private ArrayListUserDao dao = ArrayListUserDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        if (!password1.equals(password2)) {
            req.setAttribute("message", "You entered different passwords.");
            req.getRequestDispatcher("WEB-INF/pages/register.jsp").forward(req, resp);
        } else {
            if(dao.ifExist(login, password1)){
                req.setAttribute("message", "Such a user already exists. Try another login.");
                req.getRequestDispatcher("WEB-INF/pages/register.jsp").forward(req, resp);
            }else{
                dao.save(new User(login, password1, User.Role.CLIENT));
                HttpSession session = req.getSession();
                session.setAttribute("login", login);
                session.setAttribute("password", password1);
                resp.sendRedirect("index.jsp");
            }
        }
    }
}
