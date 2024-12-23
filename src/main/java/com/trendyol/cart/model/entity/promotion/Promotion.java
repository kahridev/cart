package com.trendyol.cart.model.entity.promotion;

import com.trendyol.cart.model.entity.Cart;

public abstract class Promotion {
    private final int promotionId;

    protected Promotion(int promotionId) {
        this.promotionId = promotionId;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public abstract double calculateDiscount(Cart cart);
}
