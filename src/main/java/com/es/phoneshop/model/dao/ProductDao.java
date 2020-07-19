package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.PhoneShopException;
import java.util.List;

public interface ProductDao {
    Product findProduct(Long id) throws PhoneShopException;

    void save(Product product);

    void delete(Long id);

    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);

}
