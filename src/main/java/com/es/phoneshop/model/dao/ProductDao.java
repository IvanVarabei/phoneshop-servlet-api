package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.entity.Product;

import java.util.List;

public interface ProductDao extends GenericDao<Product> {
    void updateProductImageUrl(Long productId, String imageUrl);

    void updateProductTag(Long productId, String tag);

    void updateProductDescription(Long productId, String description);

    void updateProductPrice(long productId, double price);//todo long Long

    void updateProductStock(long productId, int stock);

    List<String> findTags();

    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);
}
