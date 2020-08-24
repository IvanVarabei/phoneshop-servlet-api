package com.es.phoneshop.controller.filter;

import com.es.phoneshop.model.service.DosProtectionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DosFilterTest {
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private FilterChain filterChain;
    @Mock
    private DosProtectionService dosProtectionService;
    @InjectMocks
    private final Filter filter = new DosFilter();

    @Before
    public void setup() {
        when(req.getRemoteAddr()).thenReturn("198.34.2.45");
        when(dosProtectionService.isAllowed("198.34.2.45")).thenReturn(true);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        filter.doFilter(req, resp, filterChain);

        verify(filterChain).doFilter(req, resp);
    }

    @Test
    public void testDoGetNotAllowed() throws ServletException, IOException {
        when(dosProtectionService.isAllowed("198.34.2.45")).thenReturn(false);

        filter.doFilter(req, resp, filterChain);

        verify(resp).setStatus(429);
    }
}
