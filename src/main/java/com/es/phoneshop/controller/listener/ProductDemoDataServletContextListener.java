package com.es.phoneshop.controller.listener;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.entity.Product;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProductDemoDataServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        if (Boolean.parseBoolean(event.getServletContext().getInitParameter("isInsertDemoData"))) {
            for (Product product : getSampleProducts()) {
                ArrayListProductDao.getInstance().save(product);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    private List<Product> getSampleProducts() {
        Currency usd = Currency.getInstance("USD");
        Map<LocalDate, BigDecimal> priceHistory1 = new TreeMap<>();
        priceHistory1.put(LocalDate.of(2017, 4, 23), BigDecimal.valueOf(150));
        priceHistory1.put(LocalDate.of(2016, 9, 2), BigDecimal.valueOf(180));
        priceHistory1.put(LocalDate.of(2016, 2, 11), BigDecimal.valueOf(190));
        Map<LocalDate, BigDecimal> priceHistory2 = new TreeMap<>();
        priceHistory2.put(LocalDate.of(2018, 4, 23), BigDecimal.valueOf(900));
        priceHistory2.put(LocalDate.of(2017, 3, 2), BigDecimal.valueOf(1000));
        priceHistory2.put(LocalDate.of(2016, 8, 11), BigDecimal.valueOf(1100));
        return List.of(
                new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, priceHistory1, "manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"),
                new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, priceHistory2, "manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"),
                new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, priceHistory1, "manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"),
                new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, priceHistory2, "manufacturer/Apple/Apple%20iPhone.jpg"),
                new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, priceHistory1, "manufacturer/Apple/Apple%20iPhone%206.jpg"),
                new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, priceHistory2, "manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"),
                new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, priceHistory1, "manufacturer/Sony/Sony%20Ericsson%20C901.jpg"),
                new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, priceHistory1, "manufacturer/Sony/Sony%20Xperia%20XZ.jpg"),
                new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, priceHistory1, "manufacturer/Nokia/Nokia%203310.jpg"),
                new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, priceHistory1, "manufacturer/Palm/Palm%20Pixi.jpg"),
                new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, priceHistory1, "manufacturer/Siemens/Siemens%20C56.jpg"),
                new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, priceHistory1, "manufacturer/Siemens/Siemens%20C61.jpg"),
                new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, priceHistory1, "manufacturer/Siemens/Siemens%20SXG75.jpg"));
    }
}
