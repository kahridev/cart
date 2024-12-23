package com.trendyol.cart.store;

import com.trendyol.cart.model.entity.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartTotalsObserver implements CartObserver {

    @Override
    public void onCartUpdated(Cart cart) {
        cart.setTotalPrice(cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum());
    }
}
