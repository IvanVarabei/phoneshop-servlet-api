package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.storage.PhoneStock;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.PhoneShopException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    @Mock
    private PhoneStock phoneStock;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private Product productWithExistingId;
    private List<Product> productList;
    @InjectMocks
    private final ArrayListProductDao dao = new ArrayListProductDao();

    @Before
    public void setup() {
        productList = new ArrayList<>();
        when(phoneStock.getPhoneList()).thenReturn(productList);
        when(product1.getId()).thenReturn(1L);
        when(product1.getPrice()).thenReturn(new BigDecimal(1000));
        when(product1.getStock()).thenReturn(5);
        when(product2.getId()).thenReturn(2L);
        when(product3.getId()).thenReturn(3L);
        when(product3.getPrice()).thenReturn(new BigDecimal(900));
        when(product3.getStock()).thenReturn(3);
        when(productWithExistingId.getId()).thenReturn(2L);
    }

    @Test
    public void testSaveIdWhichExists() {
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        dao.save(productWithExistingId);
        boolean isContainProductWithExistingId = productList.contains(productWithExistingId);
        boolean isContainProduct2 = productList.contains(product2);

        assertTrue(isContainProductWithExistingId);
        assertFalse(isContainProduct2);
    }

    @Test
    public void testSaveNullId() {
        when(product1.getId()).thenReturn(null);

        dao.save(product1);
        boolean actual = productList.contains(product1);

        assertTrue(actual);
    }

    @Test
    public void testSave() {
        dao.save(product1);
        boolean actual = productList.contains(product1);

        assertTrue(actual);
    }

    @Test
    public void testDelete() {
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        dao.delete(2L);
        boolean actual = productList.contains(product2);

        assertFalse(actual);
    }

    @Test
    public void testFindProduct() throws PhoneShopException {
        productList.add(product1);
        productList.add(product2);
        Product actual = dao.findProduct(2L);
        Product expected = product2;

        assertEquals(expected, actual);
    }

    @Test
    public void testFindNotNullCostNotZeroStockProducts() {
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        List<Product> expected = List.of(product1, product3);
        List<Product> actual = dao.findProducts();

        assertEquals(expected, actual);
    }

    @Test(expected = PhoneShopException.class)
    public void testFindProductWhichDoesNotExist() throws PhoneShopException {
        dao.findProduct(-1L);
    }
}
