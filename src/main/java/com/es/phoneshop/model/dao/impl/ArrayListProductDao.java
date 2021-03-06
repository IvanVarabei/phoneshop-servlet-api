package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.entity.Product;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        public static final ArrayListProductDao ARRAY_LIST_PRODUCT_DAO_INSTANCE = new ArrayListProductDao();

        private SingletonHolder() {
        }
    }

    public static ArrayListProductDao getInstance() {
        return SingletonHolder.ARRAY_LIST_PRODUCT_DAO_INSTANCE;
    }

    @Override
    public synchronized void updateProductImageUrl(long productId, String imageUrl) {
        items.stream().filter(p -> p.getId().equals(productId)).findAny().ifPresent(p -> p.setImageUrl(imageUrl));
    }

    @Override
    public synchronized void updateProductDescription(long productId, String description) {
        items.stream().filter(p -> p.getId().equals(productId)).findAny().ifPresent(p -> p.setDescription(description));
    }

    @Override
    public synchronized void updateProductTag(long productId, String tag) {
        items.stream().filter(p -> p.getId().equals(productId)).findAny().ifPresent(p -> p.setTag(tag));
    }

    @Override
    public synchronized void updateProductPrice(long productId, double price) {
        items.stream().filter(i -> i.getId().equals(productId)).findAny()
                .ifPresent(p -> {
                    p.setPrice(BigDecimal.valueOf(price));
                    p.setPriceHistory(LocalDate.now(), BigDecimal.valueOf(price));
                });
    }

    @Override
    public synchronized void updateProductStock(long productId, int stockValue) {
        items.stream().filter(i -> i.getId().equals(productId)).findAny().ifPresent(p -> p.setStock(stockValue));
    }

    @Override
    public synchronized List<String> findTags() {
        return items.stream().map(Product::getTag).distinct().collect(Collectors.toList());
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
