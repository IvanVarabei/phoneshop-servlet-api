package com.es.phoneshop.model.service;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.CartItem;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.exception.OutOfStockException;

public class CartService {
    private ArrayListProductDao dao = ArrayListProductDao.getInstance();

    private CartService() {
    }

    private static class CartServiceHolder {
        private static final CartService CART_SERVICE_INSTANCE = new CartService();
    }

    public static CartService getInstance() {
        return CartServiceHolder.CART_SERVICE_INSTANCE;
    }

    public synchronized void add(Cart cart, Long productId, int quantity) throws OutOfStockException {
        Product product;
        try {
            product = dao.findProduct(productId);
            int cartAmount = cart.getCartItemList().stream()
                    .filter(p -> p.getProduct().getId().equals(productId))
                    .findAny().orElse(new CartItem(null, 0)).getQuantity();
            if (product.getStock() - cartAmount < quantity) {
                throw new OutOfStockException(product.getStock() - cartAmount);
            }
        } catch (ItemNotFoundException e) {
            throw new OutOfStockException(e, 0);
        }
        cart.add(new CartItem(product, quantity));
    }
}
