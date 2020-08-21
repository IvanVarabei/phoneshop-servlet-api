package com.es.phoneshop.controller.filter;

import com.es.phoneshop.model.service.DosProtectionService;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {
    private DosProtectionService dosProtectionService = DosProtectionService.getInstance();

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        if (dosProtectionService.isAllowed(req.getRemoteAddr())) {
            filterChain.doFilter(req, resp);
        } else {
            ((HttpServletResponse) resp).setStatus(429);
        }
    }

    @Override
    public void destroy() {
    }
}
