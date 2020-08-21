package com.es.phoneshop.model.service;

import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.CartItem;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.value.Const;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class CartService {
    private CartService() {
    }

    private static class CartServiceHolder {
        private static final CartService CART_SERVICE_INSTANCE = new CartService();
    }

    public static CartService getInstance() {
        return CartServiceHolder.CART_SERVICE_INSTANCE;
    }

    public synchronized void add(HttpSession session, Product product, int quantity) throws OutOfStockException {
        Cart cart = extractCartOrCreateNewOne(session);
        int cartAmount = cart.getCartItemList().stream()
                .filter(p -> p.getProduct().getId().equals(product.getId()))
                .findAny()
                .map(CartItem::getQuantity)
                .orElse(0);
        update(session, product, quantity + cartAmount);
    }

    public synchronized void update(HttpSession session, Product product, int quantity) throws OutOfStockException {
        Cart cart = extractCartOrCreateNewOne(session);
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
        Cart cart = extractCartOrCreateNewOne(session);
        cart.getCartItemList().removeIf(cartItem -> cartItem.getProduct().equals(product));
        refreshCart(cart);
    }

    public void clearCart(Cart cart){
        cart.getCartItemList().clear();
    }

    private void refreshCart(Cart cart) {
        cart.setTotalCost(cart.getCartItemList().stream()
                .map(cartItem -> cartItem
                        .getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0)));
    }

    private Cart extractCartOrCreateNewOne(HttpSession session) {
        if (session.getAttribute(Const.RequestAttribute.CART) == null) {
            session.setAttribute(Const.RequestAttribute.CART, new Cart());
        }
        return (Cart) session.getAttribute(Const.RequestAttribute.CART);
    }
}
