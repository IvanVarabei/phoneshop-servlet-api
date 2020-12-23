package com.es.phoneshop.integration;

import com.es.phoneshop.controller.servlet.EditProductPageServlet;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.entity.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EditProductIntegrationTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    private final EditProductPageServlet servlet = new EditProductPageServlet();
    private ProductDao productDao = ArrayListProductDao.getInstance();
    private Currency usd = Currency.getInstance("USD");
    private List<Product> products;

    private List<Product> getSampleProducts() {
        List<Product> productList = List.of(
                new Product("Samsung", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "a"),
                new Product("Samsung", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "a"),
                new Product("Samsung", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "a"),
                new Product("Apple", "Apple iPhone", new BigDecimal(200), usd, 10, "a"));
        for (int i = 0; i < productList.size(); i++) {
            productList.get(i).setId((long) i + 1);
        }
        return productList;
    }

    @Before
    public void setup() {
        products = getSampleProducts();
        productDao.setItems(products);
        when(request.getContextPath()).thenReturn("contextPath");
        when(request.getParameterValues("productId")).thenReturn(new String[]{"1", "2"});
        when(request.getParameterValues("imageUrl")).thenReturn(new String[]{"1url", "2url"});
        when(request.getParameterValues("tag")).thenReturn(new String[]{"htc", "palm"});
        when(request.getParameterValues("description")).thenReturn(new String[]{"d1", "d2"});
        when(request.getParameterValues("price")).thenReturn(new String[]{"1", "2"});
        when(request.getParameterValues("stock")).thenReturn(new String[]{"1", "2"});
        when(request.getRequestDispatcher("WEB-INF/pages/editProduct.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void doGetFindAllProducts() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute("products", getSampleProducts());
    }

    @Test
    public void doGetFindProductsByParameters() throws ServletException, IOException {
        when(request.getParameter("searchTag")).thenReturn("Samsung");
        when(request.getParameter("minPrice")).thenReturn("70");
        when(request.getParameter("maxPrice")).thenReturn("200");
        when(request.getParameter("searchStock")).thenReturn("80");
        Product soughtProduct = new Product("Samsung", "Samsung Galaxy S", new BigDecimal(100),
                usd, 100, "a");
        soughtProduct.setId(1l);

        servlet.doGet(request, response);

        verify(request).setAttribute("products", List.of(soughtProduct));
    }

    @Test
    public void doPostNoErrorsUpdateProducts() throws ServletException, IOException {
        List<Product> actual = products;
        List<Product> expected = List.of(
                new Product("htc", "d1", BigDecimal.valueOf(1d), usd, 1, "1url"),
                new Product("palm", "d2", BigDecimal.valueOf(2d), usd, 2, "2url"),
                new Product("Samsung", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "a"),
                new Product("Apple", "Apple iPhone", new BigDecimal(200), usd, 10, "a"));
        for (int i = 0; i < expected.size(); i++) {
            expected.get(i).setId((long) i + 1);
        }

        servlet.doPost(request, response);

        assertEquals(expected, actual);
    }

    @Test
    public void doPostFirstProductHasWrongPriceWillNotBeUpdated() throws ServletException, IOException {
        when(request.getParameterValues("price")).thenReturn(new String[]{"notParsed", "2"});
        List<Product> actual = products;
        List<Product> expected = List.of(
                new Product("htc", "d1", BigDecimal.valueOf(100), usd, 1, "1url"),
                new Product("palm", "d2", BigDecimal.valueOf(2d), usd, 2, "2url"),
                new Product("Samsung", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "a"),
                new Product("Apple", "Apple iPhone", new BigDecimal(200), usd, 10, "a"));
        for (int i = 0; i < expected.size(); i++) {
            expected.get(i).setId((long) i + 1);
        }

        servlet.doPost(request, response);

        assertEquals(expected, actual);
    }
}
