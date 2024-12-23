package com.trendyol.cart.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddItemPayload extends CommandPayload {

    private int itemId;
    private int categoryId;
    private int sellerId;
    private double price;
    private int quantity;

    @Override
    public void validate() {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("CategoryId is required and must be positive.");
        } else if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        } else if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        } else if (sellerId <= 0) {
            throw new IllegalArgumentException("SellerId must be greater than 0.");
        } else if (itemId <= 0) {
            throw new IllegalArgumentException("ItemId must be greater than 0.");
        }
    }
}
