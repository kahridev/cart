package com.trendyol.cart.model.entity.item;

import com.trendyol.cart.command.validator.ValidationChain;
import com.trendyol.cart.command.validator.vasItem.VasItemLimitValidationHandler;
import com.trendyol.cart.command.validator.vasItem.VasItemLinkedItemValidationHandler;
import com.trendyol.cart.command.validator.vasItem.VasItemPriceValidationHandler;
import com.trendyol.cart.model.entity.Cart;
import lombok.Getter;

@Getter
public class VasItem extends Item {
    private int vasItemId;
    private int vasSellerId;
    private int vasCategoryId;

    public VasItem(Item item, double price, int quantity, int vasItemId, int vasSellerId, int vasCategoryId) {
        super(item.getItemId(), price, quantity, item.getCategoryId(), item.getSellerId());
        this.vasItemId = vasItemId;
        this.vasSellerId = vasSellerId;
        this.vasCategoryId = vasCategoryId;
    }

    @Override
    public void validate(Cart cart) {
        new ValidationChain()
                .addHandler(new VasItemLinkedItemValidationHandler())
                .addHandler(new VasItemLimitValidationHandler())
                .addHandler(new VasItemPriceValidationHandler())
                .validate(this, cart);
    }

    @Override
    public void addToCart(Cart cart) {
        DefaultItem defaultItem = (DefaultItem) cart.getItems().stream()
                .filter(item -> item instanceof DefaultItem && item.getItemId() == getItemId())
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("DefaultItem with ID " + getItemId() + " not found in the cart."));

        defaultItem.getVasItems().add(this);
    }
}
