package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.PhoneShopException;
import org.junit.BeforeClass;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ArrayListProductDaoTest {
    private static final ProductDao productDao = new ArrayListProductDao();
    private static final Currency USD = Currency.getInstance("USD");

    @BeforeClass
    public static void setup() {
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), USD, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), USD, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), USD, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productDao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), USD, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        productDao.save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), USD, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
    }

    @Test
    public void testFindProducts() {
        List<Product> expected = new ArrayList<>();
        expected.add(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), USD, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        expected.add(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), USD, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        expected.add(new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), USD, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        expected.add(new Product(5L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), USD, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        List<Product> actual = productDao.findProducts();
        assertEquals(actual, expected);
    }

    @Test(expected = PhoneShopException.class)
    public void testFindProductWhichDoesNotExist() throws PhoneShopException {
        Product actual = productDao.findProduct(-1L);
    }

    @Test
    public void testFindProduct() {
        Product actual = null;
        Product expected = new Product(5L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), USD, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg");
        try {
            actual = productDao.findProduct(5L);
        } catch (PhoneShopException e) {
            fail();
        }
        assertEquals(actual, expected);
    }

    @Test
    public void testSave() {
        Product actual = null;
        Product expected = new Product(6L, "mi3", "xiaomi mi8", new BigDecimal(320), USD, 0, "");
        productDao.save(expected);
        try {
            actual = productDao.findProduct(6L);
        } catch (PhoneShopException e) {
            fail();
        }
        assertEquals(actual, expected);
    }

    @Test(expected = PhoneShopException.class)
    public void testDelete() throws PhoneShopException {
        productDao.delete(2L);
        productDao.findProduct(2L);
    }
}
