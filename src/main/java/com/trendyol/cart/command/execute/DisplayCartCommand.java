package com.trendyol.cart.command.execute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.request.DisplayCartPayload;
import com.trendyol.cart.model.response.CommandResult;
import com.trendyol.cart.store.CartStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DisplayCartCommand implements Command<DisplayCartPayload> {

    private final CartStore cartStore;
    private final ObjectMapper objectMapper;

    @Override
    public CommandResult execute(DisplayCartPayload payload) {
        try {
            Cart cart = cartStore.getCart();
            return new CommandResult(true, objectMapper.writeValueAsString(cart));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting cart to JSON", e);
        }
    }
}
