package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.exception.PhoneShopException;
import com.es.phoneshop.model.entity.PhoneStock;
import com.es.phoneshop.model.entity.Product;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    @Override
    public synchronized Product findProduct(Long id) throws PhoneShopException {
        return PhoneStock.getInstance().getPhoneList().stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow(PhoneShopException::new);
    }

    @Override
    public synchronized List<Product> findProducts() {
        return PhoneStock.getInstance().getPhoneList().stream()
                .filter(product -> product.getPrice() != null)
                .filter(product -> product.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        List<Product> productList = PhoneStock.getInstance().getPhoneList();
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId().equals(product.getId())) {
                productList.set(i, product);
                return;
            }
        }
        product.setId((productList.isEmpty()) ? 1L : productList.get(productList.size() - 1).getId() + 1);
        productList.add(product);
    }

    @Override
    public synchronized void delete(Long id) {
        List<Product> productList = PhoneStock.getInstance().getPhoneList();
        productList.removeIf(p -> id.equals(p.getId()));
    }
}
