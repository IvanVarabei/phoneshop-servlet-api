package com.es.phoneshop.controller.filter;

import com.es.phoneshop.value.Const;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {
    private static final String LOGIN_JSP = "WEB-INF/pages/login.jsp";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        if (session.getAttribute(Const.AttributeKey.LOGIN) != null) {
            filterChain.doFilter(req, resp);
        } else {
            session.setAttribute(Const.AttributeKey.DESIRABLE_SECURED_PAGE, req.getServletPath());
            req.getRequestDispatcher(LOGIN_JSP).forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }
}