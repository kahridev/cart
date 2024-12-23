package com.trendyol.cart.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trendyol.cart.command.validator.ValidationChain;
import com.trendyol.cart.command.validator.cart.TotalQuantityValidationHandler;
import com.trendyol.cart.command.validator.cart.UniqueItemValidationHandler;
import com.trendyol.cart.model.entity.item.Item;
import com.trendyol.cart.store.CartObserverManager;
import com.trendyol.cart.store.CartTransaction;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {
    private List<Item> items = new ArrayList<>();
    private double totalDiscount;
    private double totalAmount;
    private Integer appliedPromotionId;

    @JsonIgnore
    private double totalPrice;

    @JsonIgnore
    private final CartObserverManager observerManager;

    private static final ValidationChain cartValidationChain = new ValidationChain()
            .addHandler(new UniqueItemValidationHandler())
            .addHandler(new TotalQuantityValidationHandler());

    public Cart(CartObserverManager observerManager) {
        this.observerManager = observerManager;
    }

    public boolean addItem(Item item) {
        CartTransaction transaction = new CartTransaction(this);

        cartValidationChain.validate(item, this);
        item.validate(this);
        item.addToCart(this);
        observerManager.notifyObservers(this);

        if (getTotalAmount() > 500000) {
            transaction.rollback();
            throw new IllegalArgumentException("Total cart amount cannot exceed 500,000 TL.");
        }

        return true;
    }

    // TODO Since I'm not sure if the deletion of VasItems is intended to be managed through a separate command, I am
    // ignoring it here for now.
    public boolean removeItem(int itemId) {
        var result = items.removeIf(item -> item.getItemId() == itemId);
        if (result) {
            observerManager.notifyObservers(this);
        }
        return result;
    }

    public void resetCart() {
        items.clear();
        totalDiscount = 0;
        totalAmount = 0;
        totalPrice = 0;
        appliedPromotionId = 0;
        observerManager.notifyObservers(this);
    }
}
