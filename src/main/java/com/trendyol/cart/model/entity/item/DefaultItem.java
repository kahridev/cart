package com.trendyol.cart.model.entity.item;

import com.trendyol.cart.model.entity.Cart;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class DefaultItem extends Item {
    private final List<VasItem> vasItems = new ArrayList<>();

    public DefaultItem(int itemId, int categoryId, int sellerId, double price, int quantity) {
        super(itemId, price, quantity, categoryId, sellerId);
    }

    @Override
    public void validate(Cart cart) {
        if (5003 == getSellerId()) {
            throw new IllegalArgumentException("SellerId cannot be given value!");
        }
    }

    @Override
    public void addToCart(Cart cart) {
        cart.getItems().add(this);
    }
}
