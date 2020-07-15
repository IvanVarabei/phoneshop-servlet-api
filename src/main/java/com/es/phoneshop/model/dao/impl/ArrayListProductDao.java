package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.exception.PhoneShopException;
import com.es.phoneshop.model.dao.storage.PhoneStock;
import com.es.phoneshop.model.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private PhoneStock phoneStock = PhoneStock.getInstance();
    private static long productIdCounter;

    @Override
    public synchronized Product findProduct(Long id) throws PhoneShopException {
        return phoneStock.getPhoneList().stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow(PhoneShopException::new);
    }

    @Override
    public synchronized List<Product> findProducts() {
        return phoneStock.getPhoneList().stream()
                .filter(product -> product.getPrice() != null)
                .filter(product -> product.getStock() > 0)
                .collect(Collectors.toList());
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
