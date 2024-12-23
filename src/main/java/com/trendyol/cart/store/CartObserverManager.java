package com.trendyol.cart.store;

import com.trendyol.cart.model.entity.Cart;
import java.util.ArrayList;
import java.util.List;

public class CartObserverManager {
    private final List<CartObserver> observers = new ArrayList<>();

    public void addObserver(CartObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(Cart cart) {
        observers.forEach(observer -> observer.onCartUpdated(cart));
    }
}
