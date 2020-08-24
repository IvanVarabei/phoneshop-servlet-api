package com.es.phoneshop.model.service;

import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.service.impl.DefaultRecentlyViewedService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRecentlyViewedServiceTest {
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private Product product4;
    @Mock
    private HttpSession session;
    private Queue<Product> recentProducts;
    private final RecentlyViewedService recentlyViewedService = DefaultRecentlyViewedService.getInstance();

    @Before
    public void setup() {
        recentProducts = new LinkedList<>();
        recentProducts.offer(product1);
        recentProducts.offer(product2);
        when(session.getAttribute("recent")).thenReturn(recentProducts);
    }

    @Test
    public void testUpdateRecentlyViewedLine() {
        recentlyViewedService.updateRecentlyViewedLine(session, product1);

        verify(session, times(2)).getAttribute("recent");
        verify(session).setAttribute("recent", recentProducts);
    }

    @Test
    public void testUpdateRecentlyViewedLineQueueIsNull() {
        when(session.getAttribute("recent")).thenReturn(null);

        recentlyViewedService.updateRecentlyViewedLine(session, product1);

        verify(session, times(1)).getAttribute("recent");
        verify(session).setAttribute("recent", List.of(product1));
    }

    @Test
    public void testUpdateRecentlyViewedLinePoll() {
        recentProducts.offer(product3);

        recentlyViewedService.updateRecentlyViewedLine(session, product4);

        verify(session, times(2)).getAttribute("recent");
        verify(session).setAttribute("recent", List.of(product2, product3, product4));
    }
}
