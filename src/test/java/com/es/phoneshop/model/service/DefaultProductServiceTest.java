package com.es.phoneshop.model.service;

import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.service.impl.DefaultProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultProductServiceTest {
    private ProductService productService = DefaultProductService.getInstance();
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private Product product4;
    @Mock
    private Product product5;

    @Before
    public void setup(){
        when(product1.getTag()).thenReturn("1");
        when(product1.getStock()).thenReturn(1);

        when(product2.getTag()).thenReturn("2");
        when(product2.getStock()).thenReturn(2);
        when(product2.getPrice()).thenReturn(BigDecimal.valueOf(2));

        when(product3.getTag()).thenReturn("3");
        when(product3.getStock()).thenReturn(3);
        when(product3.getPrice()).thenReturn(BigDecimal.valueOf(3));

        when(product4.getTag()).thenReturn("4");
        when(product4.getStock()).thenReturn(4);
        when(product4.getPrice()).thenReturn(BigDecimal.valueOf(4));

        when(product5.getTag()).thenReturn("5");
    }

    @Test
    public void filterProductsByFields() {
        List<Product> benchmarkData = List.of(product1, product2, product3, product4, product5);

        List<Product> actual =productService.filterProductsByFields(
            benchmarkData,3d, 3d, 2, new String[] {"1","2", "3","4"});
        List<Product> expected = List.of(product3);

        assertEquals(expected, actual);
    }
}
