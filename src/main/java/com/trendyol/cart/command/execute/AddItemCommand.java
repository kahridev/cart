package com.trendyol.cart.command.execute;

import com.trendyol.cart.factory.ItemFactory;
import com.trendyol.cart.model.entity.item.Item;
import com.trendyol.cart.model.request.AddItemToItemPayload;
import com.trendyol.cart.model.response.CommandResult;
import com.trendyol.cart.store.CartStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddItemCommand implements Command<AddItemToItemPayload> {

    private final CartStore cartStore;
    private final ItemFactory itemFactory;

    @Override
    public CommandResult execute(AddItemToItemPayload payload) {
        payload.validate();

        Item item = itemFactory.createItem(
                payload.getCategoryId(),
                payload.getItemId(),
                payload.getSellerId(),
                payload.getPrice(),
                payload.getQuantity());

        boolean result = cartStore.getCart().addItem(item);
        return new CommandResult(result, result ? "Item added successfully" : "Failed to add item");
    }
}
