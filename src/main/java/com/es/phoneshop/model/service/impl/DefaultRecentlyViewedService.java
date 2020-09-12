package com.es.phoneshop.model.service.impl;

import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.service.RecentlyViewedService;
import com.es.phoneshop.value.Const;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.Queue;

public class DefaultRecentlyViewedService implements RecentlyViewedService {
    private static final int RECENT_VIEWED_AMOUNT = 3;

    private DefaultRecentlyViewedService() {
    }

    private static class RecentlyViewedServiceHolder {
        private static final DefaultRecentlyViewedService DEFAULT_RECENTLY_VIEWED_SERVICE_INSTANCE =
                new DefaultRecentlyViewedService();
    }

    public static DefaultRecentlyViewedService getInstance() {
        return RecentlyViewedServiceHolder.DEFAULT_RECENTLY_VIEWED_SERVICE_INSTANCE;
    }

    public void updateRecentlyViewedLine(HttpSession session, Product product) {
        Queue<Product> recent = session.getAttribute(Const.RequestAttribute.RECENT) == null ? new LinkedList<>() :
                (Queue<Product>) session.getAttribute(Const.RequestAttribute.RECENT);
        if (!recent.contains(product)) {
            recent.offer(product);
        }
        if (recent.size() > RECENT_VIEWED_AMOUNT) {
            recent.poll();
        }
        session.setAttribute(Const.RequestAttribute.RECENT, recent);
    }
}
