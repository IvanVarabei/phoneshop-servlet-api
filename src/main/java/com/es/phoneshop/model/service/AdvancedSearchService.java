package com.es.phoneshop.model.service;

import com.es.phoneshop.model.entity.Product;

import java.util.List;

public interface AdvancedSearchService {
    List<Product> searchAdvancedProducts(String productCode, Double minPrice, Double maxPrice, Integer minStock);
}
