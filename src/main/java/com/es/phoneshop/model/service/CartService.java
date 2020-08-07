package com.es.phoneshop.model.service;

import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.CartItem;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.OutOfStockException;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public class CartService {
    private static final String SESSION_ATTRIBUTE_CART = "cart";

    private CartService() {
    }

    private static class CartServiceHolder {
        private static final CartService CART_SERVICE_INSTANCE = new CartService();
    }

    public static CartService getInstance() {
        return CartServiceHolder.CART_SERVICE_INSTANCE;
    }

    public synchronized void add(HttpSession session, Product product, int quantity) throws OutOfStockException {
        if (session.getAttribute(SESSION_ATTRIBUTE_CART) == null) {
            session.setAttribute(SESSION_ATTRIBUTE_CART, new Cart());
        }
        Cart cart = (Cart) session.getAttribute(SESSION_ATTRIBUTE_CART);
        int cartAmount = cart.getCartItemList().stream()
                .filter(p -> p.getProduct().getId().equals(product.getId()))
                .findAny()
                .map(CartItem::getQuantity)
                .orElse(0);
        if (product.getStock() - cartAmount < quantity || quantity <= 0) {
            throw new OutOfStockException(product.getStock() - cartAmount);
        }
        update(session, product, quantity + cartAmount);
    }

    public synchronized void update(HttpSession session, Product product, int quantity) throws OutOfStockException {
        Cart cart = (Cart) session.getAttribute(SESSION_ATTRIBUTE_CART);
        if (product.getStock() < quantity || quantity <= 0) {
            throw new OutOfStockException(product.getStock());
        }
        cart.getCartItemList().stream()
                .filter(cartItem -> cartItem.getProduct().equals(product)).findAny()
                .ifPresentOrElse(cartItem -> cartItem.setQuantity(quantity),
                        () -> cart.getCartItemList().add(new CartItem(product, quantity)));
        refreshCart(cart);
    }

    public synchronized void delete(HttpSession session, Product product) {
        Cart cart = (Cart) session.getAttribute(SESSION_ATTRIBUTE_CART);
        List<CartItem> cartItemList = cart.getCartItemList();
        for (int i = 0; i < cartItemList.size(); i++) {
            if (cartItemList.get(i).getProduct().equals(product)) {
                cartItemList.remove(i);
                refreshCart(cart);
                return;
            }
        }
    }

    private void refreshCart(Cart cart) {
        cart.setTotalCost(cart.getCartItemList().stream().map(cartItem -> cartItem.getProduct().getPrice().multiply(
                new BigDecimal(cartItem.getQuantity()))).reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0)));
    }
}
