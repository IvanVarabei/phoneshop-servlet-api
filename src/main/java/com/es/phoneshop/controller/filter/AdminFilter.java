package com.es.phoneshop.controller.filter;

import com.es.phoneshop.model.entity.User;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.service.UserService;
import com.es.phoneshop.model.service.impl.DefaultUserService;
import com.es.phoneshop.value.Const;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    private static final String LOGIN_JSP = "WEB-INF/pages/login.jsp";
    private static final String REDIRECT_AFTER_REJECTION = "%s/products?message=You do not have enough rights " +
            "to access this page. If you are a moderator, logout and login as moderator.";
    private UserService userService = DefaultUserService.getInstance();

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        String login = (String) session.getAttribute(Const.AttributeKey.LOGIN);
        if (login != null) {
            try {
                if (userService.findByLogin(login).getRole().equals(User.Role.ADMIN)) {
                    filterChain.doFilter(req, resp);
                } else {
                    resp.sendRedirect(String.format(REDIRECT_AFTER_REJECTION, req.getContextPath()));
                }
            } catch (ItemNotFoundException ignored) {
            }
        } else {
            session.setAttribute(Const.AttributeKey.DESIRABLE_SECURED_PAGE, req.getServletPath());
            req.getRequestDispatcher(LOGIN_JSP).forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }
}