package com.es.phoneshop.model.service.impl;

import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.CartItem;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.value.Const;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class DefaultCartService implements CartService {
    private DefaultCartService() {
    }

    private static class CartServiceHolder {
        private static final DefaultCartService DEFAULT_CART_SERVICE_INSTANCE = new DefaultCartService();
    }

    public static DefaultCartService getInstance() {
        return CartServiceHolder.DEFAULT_CART_SERVICE_INSTANCE;
    }

    @Override
    public void add(HttpSession session, Product product, int quantity) throws OutOfStockException {
        Cart cart = extractCartOrSetNewOne(session);
        int cartAmount = cart.getCartItemList().stream()
                .filter(p -> p.getProduct().getId().equals(product.getId()))
                .findAny()
                .map(CartItem::getQuantity)
                .orElse(0);
        update(session, product, quantity + cartAmount);
    }

    @Override
    public void update(HttpSession session, Product product, int quantity) throws OutOfStockException {
        Cart cart = extractCartOrSetNewOne(session);
        if (product.getStock() < quantity || quantity <= 0) {
            throw new OutOfStockException(product.getStock());
        }
        cart.getCartItemList().stream()
                .filter(cartItem -> cartItem.getProduct().equals(product)).findAny()
                .ifPresentOrElse(cartItem -> cartItem.setQuantity(quantity),
                        () -> cart.getCartItemList().add(new CartItem(product, quantity)));
        refreshCart(cart);
    }

    @Override
    public void delete(HttpSession session, Product product) {
        Cart cart = extractCartOrSetNewOne(session);
        cart.getCartItemList().removeIf(cartItem -> cartItem.getProduct().equals(product));
        refreshCart(cart);
    }

    @Override
    public void clearCart(Cart cart) {
        cart.getCartItemList().clear();
    }

    @Override
    public Cart extractCartOrSetNewOne(HttpSession session) {
        if (session.getAttribute(Const.AttributeKey.CART) == null) {
            session.setAttribute(Const.AttributeKey.CART, new Cart());
        }
        return (Cart) session.getAttribute(Const.AttributeKey.CART);
    }

    private void refreshCart(Cart cart) {
        cart.setTotalCost(cart.getCartItemList().stream()
                .map(cartItem -> cartItem
                        .getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0)));
    }
}
