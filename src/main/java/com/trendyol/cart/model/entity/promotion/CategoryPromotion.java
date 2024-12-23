package com.trendyol.cart.model.entity.promotion;

import com.trendyol.cart.model.entity.Cart;
import org.springframework.stereotype.Component;

@Component
public class CategoryPromotion extends Promotion {

    private static final int TARGET_CATEGORY_ID = 3003;
    private static final double DISCOUNT_RATE = 0.05;

    public CategoryPromotion() {
        super(5676);
    }

    @Override
    public double calculateDiscount(Cart cart) {
        return cart.getItems().stream()
                .filter(item -> item.getCategoryId() == TARGET_CATEGORY_ID)
                .mapToDouble(item -> item.getPrice() * item.getQuantity() * DISCOUNT_RATE)
                .sum();
    }
}
