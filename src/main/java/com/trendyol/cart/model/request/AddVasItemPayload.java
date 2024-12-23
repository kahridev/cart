package com.trendyol.cart.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddVasItemPayload extends CommandPayload {
    private int itemId;
    private int vasItemId;
    private int vasCategoryId;
    private int vasSellerId;
    private double price;
    private int quantity;

    @Override
    public void validate() {
        if (vasCategoryId != 3242) {
            throw new IllegalArgumentException("Invalid VasItem category. Must be 3242.");
        } else if (vasSellerId != 5003) {
            throw new IllegalArgumentException("Invalid VasItem seller. Must be 5003.");
        } else if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        } else if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
    }
}
