package com.es.phoneshop.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    private List<CartItem> cartItemList = new ArrayList<>();

    public List<CartItem> getCartItemList(){
        return Collections.unmodifiableList(cartItemList);
    }

    public void add(CartItem item){
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getProduct().equals(item.getProduct())) {
                cartItem.setQuantity(item.getQuantity() + cartItem.getQuantity());
                return;
            }
        }
        cartItemList.add(item);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cart:");
        for(CartItem item : cartItemList){
            sb.append(item.toString());
        }
        return sb.toString();
    }
}
