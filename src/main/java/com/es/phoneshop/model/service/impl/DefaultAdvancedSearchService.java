package com.es.phoneshop.model.service.impl;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.service.AdvancedSearchService;

import java.util.List;

public class DefaultAdvancedSearchService implements AdvancedSearchService {
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    private DefaultAdvancedSearchService() {
    }

    private static class DefaultAdvancedSearchServiceHolder {
        private static final DefaultAdvancedSearchService DEFAULT_ADVANCED_SEARCH_SERVICE_INSTANCE =
                new DefaultAdvancedSearchService();
    }

    public static DefaultAdvancedSearchService getInstance() {
        return DefaultAdvancedSearchServiceHolder.DEFAULT_ADVANCED_SEARCH_SERVICE_INSTANCE;
    }

    @Override
    public List<Product> searchAdvancedProducts(String productCode, Double minPrice, Double maxPrice, Integer minStock) {
        return productDao.findByFields(productCode, minPrice, maxPrice, minStock);
    }
}
