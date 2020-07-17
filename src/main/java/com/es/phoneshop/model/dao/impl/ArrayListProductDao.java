package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.dao.storage.PhoneStock;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.PhoneShopException;
import com.es.phoneshop.model.util.StreamUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {
    private PhoneStock phoneStock = PhoneStock.getInstance();
    private static long productIdCounter;
    private static final String BLANK = "\\p{Blank}";

    @Override
    public synchronized Product findProduct(Long id) throws PhoneShopException {
        return phoneStock.getPhoneList().stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow(PhoneShopException::new);
    }

    @Override
    public synchronized List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        Stream<Product> productStream = search(query);
        if (sortField != SortField.DEFAULT && sortOrder != SortOrder.DEFAULT) {
            productStream = productStream.sorted(Comparator.comparing(p -> defineSortField(p, sortField)));
            if (sortOrder == SortOrder.DESC) {
                productStream = StreamUtil.reverseStream(productStream);
            }
        }
        return productStream.collect(Collectors.toList());
    }

    private synchronized <U> U defineSortField(Product p, SortField sortField) {
        return (U) (SortField.DESCRIPTION == sortField ? p.getDescription() : p.getPrice());
    }

    private synchronized Stream<Product> search(String query) {
        if (query == null || query.isEmpty()) {
            return phoneStock.getPhoneList().stream();
        } else {
            return phoneStock.getPhoneList().stream()
                    .filter(product -> product.getPrice() != null)
                    .filter(product -> product.getStock() > 0)
                    .filter(p -> Arrays.stream(p.getDescription().split(BLANK))
                            .anyMatch(sub -> Arrays.stream(query.split(BLANK))
                                    .anyMatch(sub::contains)))
                    .sorted(Comparator.comparing(p -> Arrays.stream(p.getDescription().split(BLANK))
                            .filter(sub -> Arrays.stream(query.split(BLANK))
                                    .anyMatch(sub::contains))
                            .count(), Comparator.reverseOrder()));
        }
    }

    @Override
    public synchronized void save(Product product) {
        List<Product> productList = phoneStock.getPhoneList();
        if (product.getId() != null && productList.removeIf(p -> product.getId().equals(p.getId()))) {
            productList.add(product);
            return;
        }
        product.setId(++productIdCounter);
        productList.add(product);
    }

    @Override
    public synchronized void delete(Long id) {
        List<Product> productList = phoneStock.getPhoneList();
        productList.removeIf(p -> id.equals(p.getId()));
    }
}
