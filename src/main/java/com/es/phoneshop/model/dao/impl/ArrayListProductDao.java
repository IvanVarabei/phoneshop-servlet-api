package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.entity.Product;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao extends ArrayListGenericDao<Product> implements ProductDao {
    private static final String BLANK = "\\p{Blank}";

    private ArrayListProductDao() {
    }

    private static class SingletonHolder {
        public static final ArrayListProductDao HOLDER_INSTANCE = new ArrayListProductDao();

        private SingletonHolder() {
        }
    }

    public static ArrayListProductDao getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public synchronized void delete(Long id) {
        items.removeIf(p -> id.equals(p.getId()));
    }

    @Override
    public synchronized List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        if (sortField != SortField.DEFAULT && sortOrder != SortOrder.DEFAULT) {
            Comparator<Product> comparator = Comparator.comparing(p -> defineSortField(p, sortField));
            if (sortOrder == SortOrder.DESC) {
                comparator = comparator.reversed();
            }
            return search(query).sorted(comparator).collect(Collectors.toList());
        }
        return search(query).collect(Collectors.toList());
    }

    @Override
    public synchronized void updateProductStock(Product product, int stockValue) {
        items.stream().filter(i -> i.getId().equals(product.getId())).findAny().get().setStock(stockValue);
    }

    public synchronized List<Product> findByCode(String productCode) {
        return items.stream().filter(i -> i.getCode().equals(productCode)).collect(Collectors.toList());
    }

    public synchronized List<Product> findByPrice(Double minPrice, Double maxPrice) {
        return items.stream()
                .filter(i -> i.getPrice().doubleValue() <= maxPrice && i.getPrice().doubleValue() >= minPrice)
                .collect(Collectors.toList());
    }

    public synchronized List<Product> findByMinPrice(Double minPrice) {
        return items.stream().filter(i -> i.getPrice().doubleValue() >= minPrice).collect(Collectors.toList());
    }

    public synchronized List<Product> findByMaxPrice(Double maxPrice) {
        return items.stream().filter(i -> i.getPrice().doubleValue() <= maxPrice).collect(Collectors.toList());
    }

    public synchronized List<Product> findByMinStock(Integer minStock) {
        return items.stream().filter(i -> i.getStock() >= minStock).collect(Collectors.toList());
    }

    public synchronized List<Product> findByCodePriceMinStock(
            String productCode, Double minPrice, Double maxPrice, Integer minStock) {
        return items.stream()
                .filter(i -> i.getCode().equals(productCode))
                .filter(i -> i.getStock() >= minStock)
                .filter(i -> i.getPrice().doubleValue() <= maxPrice && i.getPrice().doubleValue() >= minPrice)
                .collect(Collectors.toList());
    }

    public synchronized List<Product> findByCodeMinPrice(String productCode, Double minPrice) {
        return items.stream()
                .filter(i -> i.getCode().equals(productCode))
                .filter(i -> i.getPrice().doubleValue() >= minPrice)
                .collect(Collectors.toList());
    }

    public synchronized List<Product> findByCodeMaxPrice(String productCode, Double maxPrice) {
        return items.stream()
                .filter(i -> i.getCode().equals(productCode))
                .filter(i -> i.getPrice().doubleValue() <= maxPrice)
                .collect(Collectors.toList());
    }

    public synchronized List<Product> findByCodePrice(String productCode, Double minPrice, Double maxPrice) {
        return items.stream()
                .filter(i -> i.getCode().equals(productCode))
                .filter(i -> i.getPrice().doubleValue() <= maxPrice && i.getPrice().doubleValue() >= minPrice)
                .collect(Collectors.toList());
    }

    public synchronized List<Product> findByCodeMinStock(String productCode, Integer minStock) {
        return items.stream()
                .filter(i -> i.getCode().equals(productCode))
                .filter(i -> i.getStock() >= minStock)
                .collect(Collectors.toList());
    }

    public synchronized List<Product> findByMaxPriceMinStock(Double maxPrice, Integer minStock) {
        return items.stream()
                .filter(i -> i.getStock() >= minStock)
                .filter(i -> i.getPrice().doubleValue() <= maxPrice)
                .collect(Collectors.toList());
    }

    public synchronized List<Product> findByMinPriceMinStock(Double minPrice, Integer minStock) {
        return items.stream()
                .filter(i -> i.getStock() >= minStock)
                .filter(i -> i.getPrice().doubleValue() >= minPrice)
                .collect(Collectors.toList());
    }

    public synchronized List<Product> findByPriceMinStock(Double minPrice, Double maxPrice, Integer minStock) {
        return items.stream()
                .filter(i -> i.getStock() >= minStock)
                .filter(i -> i.getPrice().doubleValue() <= maxPrice && i.getPrice().doubleValue() >= minPrice)
                .collect(Collectors.toList());
    }

    private <U> U defineSortField(Product p, SortField sortField) {
        return (U) (SortField.DESCRIPTION == sortField ? p.getDescription() : p.getPrice());
    }

    private Stream<Product> search(String query) {
        return items.stream()
                .filter(getPriceAndStockPredicate(StringUtils.isBlank(query)))
                .filter(p -> Arrays.stream(query.split(BLANK)).anyMatch(p.getDescription()::contains))
                .sorted(Comparator.comparing(p -> Arrays.stream(query.split(BLANK))
                        .filter(p.getDescription()::contains).count(), Comparator.reverseOrder()));
    }

    private Predicate<Product> getPriceAndStockPredicate(boolean queryPresent) {
        if (queryPresent) {
            return product -> product.getPrice() != null && product.getStock() > 0;
        } else {
            return product -> true;
        }
    }
}
