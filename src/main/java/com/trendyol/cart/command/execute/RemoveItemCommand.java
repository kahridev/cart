package com.trendyol.cart.command.execute;

import com.trendyol.cart.model.request.RemoveItemPayload;
import com.trendyol.cart.model.response.CommandResult;
import com.trendyol.cart.store.CartStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveItemCommand implements Command<RemoveItemPayload> {

    private final CartStore cartStore;

    @Override
    public CommandResult execute(RemoveItemPayload payload) {
        payload.validate();

        boolean result = cartStore.getCart().removeItem(payload.getItemId());

        return new CommandResult(result, result ? "Item removed successfully" : "Item not found or failed to remove");
    }
}
