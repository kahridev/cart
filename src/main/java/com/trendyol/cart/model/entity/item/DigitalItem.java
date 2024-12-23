package com.trendyol.cart.model.entity.item;

import com.trendyol.cart.model.entity.Cart;

public class DigitalItem extends Item {

    public static final int CATEGORY_ID = 7889;
    private static final int MAX_QUANTITY = 5;

    public DigitalItem(int itemId, double price, int quantity, int sellerId) {
        super(itemId, price, quantity, CATEGORY_ID, sellerId);
        if (quantity > MAX_QUANTITY) {
            throw new IllegalArgumentException("DigitalItem cannot exceed 5 in quantity.");
        }
    }

    @Override
    public void validate(Cart cart) {
        if (cart.getItems().stream()
                .anyMatch(item -> item.getItemId() == this.getItemId() && !(item instanceof DigitalItem))) {
            throw new IllegalArgumentException(
                    "Category ID mismatch: Only items with CategoryID 7889 can be DigitalItems.");
        }

        long digitalItemCount = cart.getItems().stream()
                .filter(item -> item instanceof DigitalItem)
                .mapToInt(Item::getQuantity)
                .sum();

        if (digitalItemCount + this.getQuantity() > MAX_QUANTITY) {
            throw new IllegalArgumentException("Total quantity of DigitalItems in the cart cannot exceed 5.");
        }

        if (5003 == getSellerId()) {
            throw new IllegalArgumentException("SellerId cannot be given value!");
        }
    }

    @Override
    public void addToCart(Cart cart) {
        cart.getItems().add(this);
    }
}
