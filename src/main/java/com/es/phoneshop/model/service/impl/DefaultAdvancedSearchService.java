package com.es.phoneshop.model.service.impl;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.service.AdvancedSearchService;

import java.util.List;

public class DefaultAdvancedSearchService implements AdvancedSearchService {
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    private DefaultAdvancedSearchService() {
    }

    private static class DefaultAdvancedSearchServiceHolder {
        private static final DefaultAdvancedSearchService INSTANCE = new DefaultAdvancedSearchService();
    }

    public static DefaultAdvancedSearchService getInstance(){
        return DefaultAdvancedSearchServiceHolder.INSTANCE;
    }

    @Override
    public List<Product> searchAdvancedProducts(String productCode, Double minPrice, Double maxPrice, Integer minStock) {
        if (productCode == null || productCode.isEmpty() && minPrice == null && maxPrice == null && minStock == null) {
            return productDao.findProducts("", SortField.DEFAULT, SortOrder.DEFAULT);
        }
        if (productCode != null && !productCode.isEmpty() && minPrice == null && maxPrice == null && minStock == null) {
            return productDao.findByCode(productCode);
        }
        if (productCode == null || productCode.isEmpty() && minPrice != null && maxPrice == null && minStock == null) {
            return productDao.findByMinPrice(minPrice);
        }
        if (productCode == null || productCode.isEmpty() && minPrice == null && maxPrice != null && minStock == null) {
            return productDao.findByMaxPrice(minPrice);
        }
        if (productCode == null || productCode.isEmpty() && minPrice != null && maxPrice != null && minStock == null) {
            return productDao.findByPrice(minPrice, maxPrice);
        }
        if (productCode == null || productCode.isEmpty() && minPrice == null && maxPrice == null && minStock != null) {
            return productDao.findByMinStock(minStock);
        }
        if (productCode != null && !productCode.isEmpty() && minPrice != null && maxPrice != null && minStock != null) {
            return productDao.findByCodePriceMinStock(productCode, minPrice, maxPrice, minStock);
        }
        if (productCode != null && !productCode.isEmpty() && minPrice != null && maxPrice == null && minStock == null) {
            return productDao.findByCodeMinPrice(productCode, minPrice);
        }
        if (productCode != null && !productCode.isEmpty() && minPrice == null && maxPrice != null && minStock == null) {
            return productDao.findByCodeMaxPrice(productCode, maxPrice);
        }
        if (productCode != null && !productCode.isEmpty() && minPrice != null && maxPrice != null && minStock == null) {
            return productDao.findByCodePrice(productCode, minPrice, maxPrice);
        }
        if (productCode != null && !productCode.isEmpty() && minPrice == null && maxPrice == null && minStock != null) {
            return productDao.findByCodeMinStock(productCode, minStock);
        }
        if (productCode == null || productCode.isEmpty() && minPrice == null && maxPrice != null && minStock != null) {
            return productDao.findByMaxPriceMinStock(maxPrice, minStock);
        }
        if (productCode == null || productCode.isEmpty() && minPrice != null && maxPrice == null && minStock != null) {
            return productDao.findByMinPriceMinStock(minPrice, minStock);
        }
        if (productCode == null || productCode.isEmpty() && minPrice != null && maxPrice != null && minStock != null) {
            return productDao.findByPriceMinStock(minPrice, maxPrice, minStock);
        }
        return null;
    }
}
