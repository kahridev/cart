package com.trendyol.cart.factory;

import com.trendyol.cart.model.entity.item.DefaultItem;
import com.trendyol.cart.model.entity.item.DigitalItem;
import com.trendyol.cart.model.entity.item.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemFactory {

    public Item createItem(int categoryId, int itemId, int sellerId, double price, int quantity) {
        return categoryId == DigitalItem.CATEGORY_ID
                ? new DigitalItem(itemId, price, quantity, sellerId)
                : new DefaultItem(itemId, categoryId, sellerId, price, quantity);
    }
}
