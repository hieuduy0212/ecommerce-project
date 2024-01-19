package com.hieuduy.core.services;

import com.hieuduy.core.entities.CartItem;
import com.hieuduy.core.entities.ShoppingCart;

import java.util.Map;

public interface CartService {
    void addToCart(long productId, int quantity);
    Map<String, Object> getSizeCartAndTotalPrice();

    ShoppingCart getShppingCartByUsername(String username);

    CartItem update(Long cartItemId, int quantity);
}
