package com.es.phoneshop.model.service;

import com.es.phoneshop.model.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> searchAdvancedProducts(String productCode, Double minPrice, Double maxPrice, Integer minStock);
    void saveProduct(String imageUrl, String productCode, String description, BigDecimal price, int stock);
    List<Product> findByFields(
            String[] productCods, Double minPrice, Double maxPrice, Integer minStock, List<Product> items);
}
