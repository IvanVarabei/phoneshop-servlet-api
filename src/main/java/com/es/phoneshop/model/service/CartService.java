package com.es.phoneshop.model.service;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.CartItem;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.OutOfStockException;

import javax.servlet.http.HttpSession;

public class CartService {
    private static final String SESSION_ATTRIBUTE_CART = "cart";
    private ArrayListProductDao dao = ArrayListProductDao.getInstance();

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
        if (product.getStock() - cartAmount < quantity || quantity < 0) {
            throw new OutOfStockException(product.getStock() - cartAmount);
        }
        updateCart(cart, product, quantity);
    }

    private void updateCart(Cart cart, Product product, int quantity) {
        cart.getCartItemList().stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findAny()
                .ifPresentOrElse(cartItem -> cartItem.setQuantity(quantity + cartItem.getQuantity()),
                        () -> cart.getCartItemList().add(new CartItem(product, quantity)));
    }
}
