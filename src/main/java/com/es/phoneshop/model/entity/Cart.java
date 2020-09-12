package com.es.phoneshop.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private List<CartItem> cartItemList = new ArrayList<>();
    private BigDecimal totalCost;

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cart cart = (Cart) o;
        return cartItemList != null ? cartItemList.equals(cart.cartItemList) : cart.cartItemList == null;
    }

    @Override
    public int hashCode() {
        return cartItemList != null ? cartItemList.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cart{");
        for (CartItem item : cartItemList) {
            sb.append(item.toString());
        }
        sb.append(", totalCost=").append(totalCost);
        sb.append('}');
        return sb.toString();
    }
}
