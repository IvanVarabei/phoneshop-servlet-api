package com.es.phoneshop.model.service.impl;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.service.ProductService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultProductService implements ProductService {
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    private DefaultProductService() {
    }

    private static class DefaultAdvancedSearchServiceHolder {
        private static final DefaultProductService DEFAULT_PRODUCT_SERVICE_INSTANCE =
                new DefaultProductService();
    }

    public static DefaultProductService getInstance() {
        return DefaultAdvancedSearchServiceHolder.DEFAULT_PRODUCT_SERVICE_INSTANCE;
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        return productDao.findProducts(query, sortField, sortOrder);
    }

    @Override
    public List<Product> filterProductsByFields(
            List<Product> items, Double minPrice, Double maxPrice, Integer minStock, String... productTags) {
        return items.stream()
                .filter(i -> productTags == null || productTags.length == 0 || productTags[0] == null || productTags[0].isEmpty() || Arrays.asList(productTags).contains(i.getTag()))
                .filter(i -> minStock == null || i.getStock() >= minStock)
                .filter(i -> minPrice == null || i.getPrice().doubleValue() >= minPrice)
                .filter(i -> maxPrice == null || i.getPrice().doubleValue() <= maxPrice)
                .collect(Collectors.toList());
    }

    @Override
    public  List<String> findTags() {
        return productDao.findTags();
    }

    @Override
    public Product find(long productId) throws ItemNotFoundException {
        return productDao.find(productId);
    }

    @Override
    public List<Product> findAll(){
        return productDao.findAll();
    }

    @Override
    public void saveProduct(String imageUrl, String productCode, String description, BigDecimal price, int stock) {
        Product product = new Product();
        product.setImageUrl(imageUrl);
        product.setTag(productCode);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCurrency(Currency.getInstance("USD"));
        productDao.save(product);
    }

    @Override
    public void delete(long productId) {
        productDao.delete(productId);
    }

    @Override
    public void updateImageUrl(Long productId, String imageUrl) {
        productDao.updateProductImageUrl(productId, imageUrl);
    }

    @Override
    public void updateTag(Long productId, String tag) {
        productDao.updateProductTag(productId, tag);
    }

    @Override
    public void updateDescription(Long productId, String description) {
        productDao.updateProductDescription(productId, description);
    }

    @Override
    public void updatePrice(long productId, double price) {
        productDao.updateProductPrice(productId, price);
    }

    @Override
    public void updateStock(long productId, int stock) {
        productDao.updateProductStock(productId, stock);
    }
}
