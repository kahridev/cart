package com.trendyol.cart.command.execute;

import com.trendyol.cart.model.entity.item.VasItem;
import com.trendyol.cart.model.request.AddVasItemToItemPayload;
import com.trendyol.cart.model.response.CommandResult;
import com.trendyol.cart.store.CartStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddVasItemToItemCommand implements Command<AddVasItemToItemPayload> {

    private final CartStore cartStore;

    @Override
    public CommandResult execute(AddVasItemToItemPayload payload) {
        payload.validate();

        var items = cartStore.getCart().getItems();
        var item = items.stream()
                .filter(i -> i.getItemId() == payload.getItemId())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Item with given id not found!"));

        var vasItem = new VasItem(
                item,
                payload.getPrice(),
                payload.getQuantity(),
                payload.getVasItemId(),
                payload.getVasSellerId(),
                payload.getVasCategoryId());

        boolean result = cartStore.getCart().addItem(vasItem);
        return new CommandResult(result, result ? "VasItem added successfully" : "Failed to add VasItem");
    }
}
