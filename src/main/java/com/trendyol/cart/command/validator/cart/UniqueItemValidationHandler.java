package com.trendyol.cart.command.validator.cart;

import com.trendyol.cart.command.validator.ValidationHandler;
import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.item.Item;
import com.trendyol.cart.model.entity.item.VasItem;

public class UniqueItemValidationHandler implements ValidationHandler {
    @Override
    public void validate(Item item, Cart cart) {
        if (!(item instanceof VasItem) && cart.getItems().stream().anyMatch(i -> i.getItemId() == item.getItemId())) {
            throw new IllegalArgumentException("This item added before in Cart.");
        }

        if (cart.getItems().size() >= 10
                && cart.getItems().stream().noneMatch(i -> i.getItemId() == item.getItemId())) {
            throw new IllegalArgumentException("Maximum unique item limit (10) reached.");
        }
    }
}
