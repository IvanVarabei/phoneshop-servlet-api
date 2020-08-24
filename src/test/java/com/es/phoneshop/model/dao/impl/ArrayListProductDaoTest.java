package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private Product product4;
    @Mock
    private Product productWithExistingId;
    private List<Product> productList;
    private final ArrayListProductDao dao = ArrayListProductDao.getInstance();

    @Before
    public void setup() {
        productList = new ArrayList<>();
        dao.setItems(productList);

        when(product1.getId()).thenReturn(1L);
        when(product1.getPrice()).thenReturn(new BigDecimal(100));
        when(product1.getStock()).thenReturn(5);
        when(product1.getDescription()).thenReturn("Samsung Galaxy S");

        when(product2.getId()).thenReturn(2L);
        when(product2.getDescription()).thenReturn("Samsung Galaxy S II");

        when(product3.getId()).thenReturn(3L);
        when(product3.getPrice()).thenReturn(new BigDecimal(300));
        when(product3.getStock()).thenReturn(3);
        when(product3.getDescription()).thenReturn("Samsung Galaxy S III");

        when(product4.getPrice()).thenReturn(new BigDecimal(110));
        when(product4.getStock()).thenReturn(4);
        when(product4.getDescription()).thenReturn("Nokia 3310");

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
    public void testFindProduct() throws ItemNotFoundException {
        productList.add(product1);
        productList.add(product2);
        Product actual = dao.find(2L);
        Product expected = product2;

        assertEquals(expected, actual);
    }

    @Test(expected = ItemNotFoundException.class)
    public void testFindProductWhichDoesNotExist() throws ItemNotFoundException {
        dao.find(-1L);
    }

    @Test
    public void testFindNotNullCostNotZeroStockSortDefaultProducts() {
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        List<Product> expected = List.of(product1, product3);
        List<Product> actual = dao.findProducts("", SortField.DEFAULT, SortOrder.DEFAULT);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsByQuerySortDefault() {
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        List<Product> expected = List.of(product2, product3, product1);
        List<Product> actual = dao.findProducts("Samsung Galaxy S II", SortField.DEFAULT, SortOrder.DEFAULT);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsByQueryDescriptionAsc() {
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        List<Product> expected = List.of(product1, product2, product3);
        List<Product> actual = dao.findProducts("Samsung Galaxy S II", SortField.DESCRIPTION, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsByQueryDescriptionDesc() {
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        List<Product> expected = List.of(product3, product2, product1);
        List<Product> actual = dao.findProducts("Samsung Galaxy S II", SortField.DESCRIPTION, SortOrder.DESC);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsByQueryPriceAsc() {
        when(product2.getPrice()).thenReturn(new BigDecimal(200));

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        List<Product> expected = List.of(product1, product2, product3);
        List<Product> actual = dao.findProducts("Samsung Galaxy S II", SortField.PRICE, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsByQueryPriceDesc() {
        when(product2.getPrice()).thenReturn(new BigDecimal(200));

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        List<Product> expected = List.of(product3, product2, product1);
        List<Product> actual = dao.findProducts("Samsung Galaxy S II", SortField.PRICE, SortOrder.DESC);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsPriceDesc() {
        productList.add(product1);
        productList.add(product3);
        productList.add(product4);
        List<Product> expected = List.of(product3, product4, product1);
        List<Product> actual = dao.findProducts("", SortField.PRICE, SortOrder.DESC);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsPriceAsc() {
        productList.add(product1);
        productList.add(product3);
        productList.add(product4);
        List<Product> expected = List.of(product1, product4, product3);
        List<Product> actual = dao.findProducts("", SortField.PRICE, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsDescriptionDesc() {
        productList.add(product1);
        productList.add(product3);
        productList.add(product4);
        List<Product> expected = List.of(product3, product1, product4);
        List<Product> actual = dao.findProducts("", SortField.DESCRIPTION, SortOrder.DESC);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsDescriptionAsc() {
        productList.add(product1);
        productList.add(product3);
        productList.add(product4);
        List<Product> expected = List.of(product4, product1, product3);
        List<Product> actual = dao.findProducts("", SortField.DESCRIPTION, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateProductStock() {
        productList.add(product1);
        dao.updateProductStock(product1, 3);

        verify(product1).setStock(3);
    }
}
