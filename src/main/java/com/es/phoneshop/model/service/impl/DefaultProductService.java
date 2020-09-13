package com.es.phoneshop.model.service.impl;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.service.ProductService;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public class DefaultProductService implements ProductService {
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    private DefaultProductService() {
    }

    private static class DefaultAdvancedSearchServiceHolder {
        private static final DefaultProductService DEFAULT_ADVANCED_SEARCH_SERVICE_INSTANCE =
                new DefaultProductService();
    }

    public static DefaultProductService getInstance() {
        return DefaultAdvancedSearchServiceHolder.DEFAULT_ADVANCED_SEARCH_SERVICE_INSTANCE;
    }

    @Override
    public List<Product> searchAdvancedProducts(String productCode, Double minPrice, Double maxPrice, Integer minStock) {
        return productDao.findByFields(productCode, minPrice, maxPrice, minStock);
    }

    @Override
    public void saveProduct(String imageUrl, String productCode, String description, BigDecimal price, int stock) {
        Product product = new Product();
        product.setImageUrl(imageUrl);
        product.setCode(productCode);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCurrency(Currency.getInstance("USD"));
        productDao.save(product);
    }
}
