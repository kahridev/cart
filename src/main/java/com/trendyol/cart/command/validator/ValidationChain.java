package com.trendyol.cart.command.validator;

import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.item.Item;
import java.util.ArrayList;
import java.util.List;

public class ValidationChain {
    private final List<ValidationHandler> handlers = new ArrayList<>();

    public ValidationChain addHandler(ValidationHandler handler) {
        handlers.add(handler);
        return this;
    }

    public void validate(Item item, Cart cart) {
        for (ValidationHandler handler : handlers) {
            handler.validate(item, cart);
        }
    }
}
