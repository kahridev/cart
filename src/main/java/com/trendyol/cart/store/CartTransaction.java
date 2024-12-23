package com.trendyol.cart.store;

import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.item.Item;
import java.util.ArrayList;
import java.util.List;

public class CartTransaction {
    private final Cart cart;
    private final List<Item> backupItems;
    private final double backupTotalPrice;
    private final double backupTotalDiscount;

    public CartTransaction(Cart cart) {
        this.cart = cart;
        this.backupItems = new ArrayList<>(cart.getItems());
        this.backupTotalPrice = cart.getTotalPrice();
        this.backupTotalDiscount = cart.getTotalDiscount();
    }

    public void rollback() {
        cart.getItems().clear();
        cart.getItems().addAll(backupItems);
        cart.setTotalPrice(backupTotalPrice);
        cart.setTotalDiscount(backupTotalDiscount);
        cart.getObserverManager().notifyObservers(cart);
    }
}
