package com.trendyol.cart.store;

import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.promotion.Promotion;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromotionObserver implements CartObserver {

    private final List<Promotion> promotions;

    @Override
    public void onCartUpdated(Cart cart) {
        var result = applyBestPromotion(cart);
        var discount = result.getRight();
        var appliedPromotion = result.getLeft();

        cart.setTotalDiscount(discount);
        cart.setAppliedPromotionId(appliedPromotion != null ? appliedPromotion.getPromotionId() : null);
        cart.setTotalAmount(cart.getTotalPrice() - cart.getTotalDiscount());
    }

    public Pair<Promotion, Double> applyBestPromotion(Cart cart) {
        Promotion bestPromotion = null;
        double maxDiscount = 0.0;

        for (Promotion promotion : promotions) {
            Double discount = promotion.calculateDiscount(cart);
            if (discount > maxDiscount) {
                maxDiscount = discount;
                bestPromotion = promotion;
            }
        }

        return Pair.of(bestPromotion, maxDiscount);
    }
}
