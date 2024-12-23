package com.trendyol.cart.command.execute;

import com.trendyol.cart.model.request.CommandPayload;
import com.trendyol.cart.model.response.CommandResult;
import com.trendyol.cart.store.CartStore;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResetCartCommand implements Command {
    private final CartStore cartStore;

    @Override
    public CommandResult execute(CommandPayload payload) {
        cartStore.getCart().resetCart();

        return CommandResult.builder()
                .result(true)
                .message("Cart has been reset successfully")
                .build();
    }
}
