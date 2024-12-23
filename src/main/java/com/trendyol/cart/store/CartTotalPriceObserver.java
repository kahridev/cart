package com.trendyol.cart.store;

import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.item.DefaultItem;
import org.springframework.stereotype.Component;

@Component
public class CartTotalPriceObserver implements CartObserver {

    @Override
    public void onCartUpdated(Cart cart) {
        var price = cart.getItems().stream()
                .mapToDouble(item -> {
                    double itemTotal = item.getPrice() * item.getQuantity();
                    if (item instanceof DefaultItem defaultItem) {
                        double vasTotal = defaultItem.getVasItems().stream()
                                .mapToDouble(vasItem -> vasItem.getPrice() * vasItem.getQuantity())
                                .sum();
                        return itemTotal + vasTotal;
                    }
                    return itemTotal;
                })
                .sum();
        cart.setTotalPrice(price);
    }
}
