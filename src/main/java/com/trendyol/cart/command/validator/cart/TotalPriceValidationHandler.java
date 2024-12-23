package com.trendyol.cart.command.validator.cart;

import com.trendyol.cart.command.validator.ValidationHandler;
import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.item.Item;

public class TotalPriceValidationHandler implements ValidationHandler {
    @Override
    public void validate(Item item, Cart cart) {
        double newTotalPrice = cart.getTotalPrice() + (item.getPrice() * item.getQuantity());
        if (newTotalPrice > 500000) {
            throw new IllegalArgumentException("Total cart amount cannot exceed 500,000 TL.");
        }
    }
}
