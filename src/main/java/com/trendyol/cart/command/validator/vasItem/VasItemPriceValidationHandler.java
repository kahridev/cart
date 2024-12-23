package com.trendyol.cart.command.validator.vasItem;

import com.trendyol.cart.command.validator.ValidationHandler;
import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.item.DefaultItem;
import com.trendyol.cart.model.entity.item.Item;
import com.trendyol.cart.model.entity.item.VasItem;

public class VasItemPriceValidationHandler implements ValidationHandler {
    @Override
    public void validate(Item item, Cart cart) {
        if (item instanceof VasItem vasItem) {
            DefaultItem linkedItem = cart.getItems().stream()
                    .filter(i -> i instanceof DefaultItem && i.getItemId() == vasItem.getItemId())
                    .map(i -> (DefaultItem) i)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "DefaultItem with ID " + vasItem.getItemId() + " not found in the cart."));

            if (vasItem.getPrice() > linkedItem.getPrice()) {
                throw new IllegalArgumentException("VasItem price cannot exceed the DefaultItem price.");
            }
        }
    }
}
