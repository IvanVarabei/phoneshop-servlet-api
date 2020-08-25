package com.es.phoneshop.controller.filter;

import com.es.phoneshop.model.dao.impl.ArrayListUserDao;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {
    private ArrayListUserDao dao = ArrayListUserDao.getInstance();

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        if(session.getAttribute("login") != null && session.getAttribute("password") != null){
            filterChain.doFilter(req, resp);
        }else{
            if(session.getAttribute("destination") == null ||
                    !session.getAttribute("destination").equals(req.getServletPath())){
                session.setAttribute("destination", req.getServletPath());
            }
            req.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(req,resp);
        }
    }

    @Override
    public void destroy() {

    }
}