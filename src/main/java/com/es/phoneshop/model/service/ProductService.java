package com.es.phoneshop.model.service;

import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.ItemNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void saveProduct(String imageUrl, String productCode, String description, BigDecimal price, int stock);

    void delete(long productId);

    void updateImageUrl(Long productId, String imageUrl);

    void updateTag(Long productId, String tag);

    void updateDescription(Long productId, String description);

    void updatePrice(long productId, double price); //todo

    void updateStock(long productId, int stock);

    List<String> findTags();

    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);

    Product find(long productId) throws ItemNotFoundException;

    List<Product> findAll();

    List<Product> filterProductsByFields(
            List<Product> items, Double minPrice, Double maxPrice, Integer minStock, String... productTags);
}
