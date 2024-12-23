package com.trendyol.cart.model.entity.item;

import com.trendyol.cart.model.entity.Cart;
import lombok.Getter;

@Getter
public abstract class Item {
    private final int itemId;
    private final double price;
    private int quantity;
    private final int categoryId;
    private final int sellerId;

    public Item(int itemId, double price, int quantity, int categoryId, int sellerId) {
        this.itemId = itemId;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public abstract void validate(Cart cart);

    public abstract void addToCart(Cart cart);
}
