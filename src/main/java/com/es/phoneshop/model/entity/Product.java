package com.es.phoneshop.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.LinkedHashMap;
import java.util.Map;

public class Product implements Serializable, StorageItem {
    private Long id;
    private String tag;
    private String description;
    /**
     * null means there is no price because the product is outdated or new
     */
    private BigDecimal price;
    /**
     * can be null if the price is null
     */
    private Currency currency;
    private int stock;
    private Map<LocalDate, BigDecimal> priceHistory = new LinkedHashMap<>();
    private String imageUrl;


    public Product() {
    }

    public Product(String tag, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.tag = tag;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.setPriceHistory(LocalDate.now(), price);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Map<LocalDate, BigDecimal> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(LocalDate date, BigDecimal price) {
        priceHistory.put(date, price);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if (stock != product.stock) {
            return false;
        }
        if (id != null ? !id.equals(product.id) : product.id != null) {
            return false;
        }
        if (tag != null ? !tag.equals(product.tag) : product.tag != null) {
            return false;
        }
        if (description != null ? !description.equals(product.description) : product.description != null) {
            return false;
        }
        if (price != null ? !price.equals(product.price) : product.price != null) {
            return false;
        }
        if (currency != null ? !currency.equals(product.currency) : product.currency != null) {
            return false;
        }
        if (priceHistory != null ? !priceHistory.equals(product.priceHistory) : product.priceHistory != null) {
            return false;
        }
        return imageUrl != null ? imageUrl.equals(product.imageUrl) : product.imageUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + stock;
        result = 31 * result + (priceHistory != null ? priceHistory.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id=").append(id);
        sb.append(", tag='").append(tag).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", currency=").append(currency);
        sb.append(", stock=").append(stock);
        sb.append(", priceHistory=").append(priceHistory);
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}