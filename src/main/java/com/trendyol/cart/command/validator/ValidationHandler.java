package com.trendyol.cart.command.validator;

import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.item.Item;

public interface ValidationHandler {
    void validate(Item item, Cart cart);
}
