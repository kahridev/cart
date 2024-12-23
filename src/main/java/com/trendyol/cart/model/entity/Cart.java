package com.trendyol.cart.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trendyol.cart.command.validator.ValidationChain;
import com.trendyol.cart.command.validator.cart.TotalPriceValidationHandler;
import com.trendyol.cart.command.validator.cart.TotalQuantityValidationHandler;
import com.trendyol.cart.command.validator.cart.UniqueItemValidationHandler;
import com.trendyol.cart.model.entity.item.Item;
import com.trendyol.cart.model.entity.promotion.Promotion;
import com.trendyol.cart.store.CartObserverManager;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {
    private List<Item> items = new ArrayList<>();
    private double totalDiscount;
    private double totalPrice;
    private int appliedPromotionId;
    private Promotion appliedPromotion;

    @JsonIgnore
    private final CartObserverManager observerManager;

    private static final ValidationChain validationChain = new ValidationChain()
            .addHandler(new UniqueItemValidationHandler())
            .addHandler(new TotalQuantityValidationHandler())
            .addHandler(new TotalPriceValidationHandler());

    public Cart(CartObserverManager observerManager) {
        this.observerManager = observerManager;
    }

    public boolean addItem(Item item) {
        validationChain.validate(item, this);
        item.validate(this);
        item.addToCart(this);
        observerManager.notifyObservers(this);
        return true;
    }

    public boolean removeItem(int itemId) {
        var result = items.removeIf(item -> item.getItemId() == itemId);
        // TODO Burada vasItem içinde de silme gerekebilir.
        if (result) {
            observerManager.notifyObservers(this);
        }
        return result;
    }

    public void resetCart() {
        items.clear();
        observerManager.notifyObservers(this);
    }
}
