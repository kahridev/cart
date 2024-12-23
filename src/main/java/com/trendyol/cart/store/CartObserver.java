package com.trendyol.cart.store;

import com.trendyol.cart.model.entity.Cart;

public interface CartObserver {
    void onCartUpdated(Cart cart);
}
