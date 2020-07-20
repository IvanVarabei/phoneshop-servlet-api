package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.DaoException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {
    private static final String BLANK = "\\p{Blank}";
    private static long productIdCounter;
    private List<Product> phoneList = new ArrayList<>();

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

    protected void setPhoneList(List<Product> phoneList) {
        this.phoneList = phoneList;
    }

    @Override
    public synchronized Product findProduct(Long id) throws DaoException {
        return phoneList.stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow(DaoException::new);
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

    private synchronized <U> U defineSortField(Product p, SortField sortField) {
        return (U) (SortField.DESCRIPTION == sortField ? p.getDescription() : p.getPrice());
    }

    private synchronized Stream<Product> search(String query) {
        return phoneList.stream()
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
