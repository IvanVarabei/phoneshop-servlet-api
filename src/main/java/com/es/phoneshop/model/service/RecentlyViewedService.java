package com.es.phoneshop.model.service;

import com.es.phoneshop.model.entity.Product;

import javax.servlet.http.HttpSession;

public interface RecentlyViewedService {
    void updateRecentlyViewedLine(HttpSession session, Product product);
}
