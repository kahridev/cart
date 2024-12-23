package com.trendyol.cart.model.entity.promotion;

import com.trendyol.cart.model.entity.Cart;
import org.springframework.stereotype.Component;

@Component
public class TotalPricePromotion extends Promotion {

    public TotalPricePromotion() {
        super(1232);
    }

    @Override
    public double calculateDiscount(Cart cart) {
        double totalPrice = cart.getTotalPrice();

        if (totalPrice >= 500 && totalPrice < 5000) {
            return 250.0;
        } else if (totalPrice >= 5000 && totalPrice < 10000) {
            return 500.0;
        } else if (totalPrice >= 10000 && totalPrice < 50000) {
            return 1000.0;
        } else if (totalPrice >= 50000) {
            return 2000.0;
        }
        return 0.0;
    }
}
