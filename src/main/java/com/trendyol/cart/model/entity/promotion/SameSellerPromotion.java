package com.trendyol.cart.model.entity.promotion;

import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.item.Item;
import com.trendyol.cart.model.entity.item.VasItem;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SameSellerPromotion extends Promotion {

    public SameSellerPromotion() {
        super(9909);
    }

    @Override
    public double calculateDiscount(Cart cart) {
        var sellers = cart.getItems().stream()
                .filter(item -> !(item instanceof VasItem))
                .map(Item::getSellerId)
                .collect(Collectors.toSet());

        return cart.getItems().size() > 1 && sellers.size() == 1 ? cart.getTotalPrice() * 0.10 : 0.0;
    }
}
