package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.entity.Product;

import java.util.List;

public interface ProductDao extends GenericDao<Product> {
    void delete(Long id);

    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);

    void updateProductStock(Product product, int stockValue);

    List<Product> findByFields(String productCode, Double minPrice, Double maxPrice, Integer minStock);
}
