package com.trendyol.cart.command.validator.vasItem;

import com.trendyol.cart.command.validator.ValidationHandler;
import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.item.DefaultItem;
import com.trendyol.cart.model.entity.item.Item;
import com.trendyol.cart.model.entity.item.VasItem;

public class VasItemLinkedItemValidationHandler implements ValidationHandler {
    @Override
    public void validate(Item item, Cart cart) {
        if (item instanceof VasItem vasItem) {
            DefaultItem linkedItem = cart.getItems().stream()
                    .filter(i -> i instanceof DefaultItem && i.getItemId() == vasItem.getItemId())
                    .map(i -> (DefaultItem) i)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "DefaultItem with ID " + vasItem.getItemId() + " not found in the cart."));

            if (linkedItem.getCategoryId() != 1001 && linkedItem.getCategoryId() != 3004) {
                throw new IllegalArgumentException(
                        "VasItem can only be added to Furniture (1001) or Electronics (3004) categories.");
            }
        }
    }
}
