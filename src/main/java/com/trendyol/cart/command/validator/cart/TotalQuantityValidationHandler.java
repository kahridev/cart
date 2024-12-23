package com.trendyol.cart.command.validator.cart;

import com.trendyol.cart.command.validator.ValidationHandler;
import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.item.Item;

public class TotalQuantityValidationHandler implements ValidationHandler {
    @Override
    public void validate(Item item, Cart cart) {
        if (getTotalQuantity(cart) + item.getQuantity() > 30) {
            throw new IllegalArgumentException("Total item quantity in cart cannot exceed 30.");
        }
    }

    private int getTotalQuantity(Cart cart) {
        return cart.getItems().stream().mapToInt(Item::getQuantity).sum();
    }
}
