package com.trendyol.cart.store;

import com.trendyol.cart.model.entity.Cart;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CartStore {

    private final Cart cart;
    private final CartObserverManager observerManager;

    public CartStore(List<CartObserver> observers) {
        this.observerManager = new CartObserverManager();
        this.cart = new Cart(observerManager);

        observers.forEach(observerManager::addObserver);
    }

    public Cart getCart() {
        return cart;
    }
}
