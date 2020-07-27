package com.es.phoneshop.model.service;

import com.es.phoneshop.model.entity.Product;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.Queue;

public class RecentlyViewedService {
    private static final String SESSION_ATTRIBUTE_RECENT = "recent";
    private static final int RECENT_VIEWED_AMOUNT = 3;

    private RecentlyViewedService() {
    }

    private static class RecentlyViewedServiceHolder {
        private static final RecentlyViewedService RECENTLY_VIEWED_SERVICE_INSTANCE =
                new RecentlyViewedService();
    }

    public static RecentlyViewedService getInstance() {
        return RecentlyViewedServiceHolder.RECENTLY_VIEWED_SERVICE_INSTANCE;
    }

    public void updateRecentlyViewedLine(HttpSession session, Product product) {
        Queue<Product> recent = session.getAttribute(SESSION_ATTRIBUTE_RECENT) == null ? new LinkedList<>() :
                (Queue<Product>) session.getAttribute(SESSION_ATTRIBUTE_RECENT);
        if (!recent.contains(product)) {
            recent.offer(product);
        }
        if (recent.size() > RECENT_VIEWED_AMOUNT) {
            recent.poll();
        }
        session.setAttribute(SESSION_ATTRIBUTE_RECENT, recent);
    }
}
