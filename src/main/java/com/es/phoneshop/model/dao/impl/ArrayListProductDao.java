package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.PhoneShopException;
import com.es.phoneshop.model.util.StreamUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {
    private static final String BLANK = "\\p{Blank}";
    private static long productIdCounter;
    private final List<Product> phoneList = Collections.synchronizedList(new ArrayList<>());

    private ArrayListProductDao() {
    }

    public static class SingletonHolder {
        public static final ArrayListProductDao HOLDER_INSTANCE = new ArrayListProductDao();

        private SingletonHolder() {
        }
    }

    public static ArrayListProductDao getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public synchronized Product findProduct(Long id) throws PhoneShopException {
        return phoneList.stream()
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
            return phoneList.stream();
        } else {
            return phoneList.stream()
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
        if (product.getId() != null && phoneList.removeIf(p -> product.getId().equals(p.getId()))) {
            phoneList.add(product);
            return;
        }
        product.setId(++productIdCounter);
        phoneList.add(product);
    }

    @Override
    public synchronized void delete(Long id) {
        phoneList.removeIf(p -> id.equals(p.getId()));
    }
}
