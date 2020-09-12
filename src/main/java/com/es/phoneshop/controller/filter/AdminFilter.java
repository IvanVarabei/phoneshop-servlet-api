package com.es.phoneshop.controller.filter;

import com.es.phoneshop.model.dao.impl.ArrayListUserDao;
import com.es.phoneshop.model.entity.User;
import com.es.phoneshop.model.exception.ItemNotFoundException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    private ArrayListUserDao dao = ArrayListUserDao.getInstance();

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        try {
            if(dao.find((String) session.getAttribute("login")).getRole().equals(User.Role.ADMIN)){
                filterChain.doFilter(req, resp);
            }else{
                resp.sendRedirect(req.getContextPath() +
                        "/products?message=You do not have enough rights to access this page. If you are a moderator, "+
                        "logout and login as moderator.");
            }
        } catch (ItemNotFoundException e) {
            throw null;
        }
    }

    @Override
    public void destroy() {

    }
}