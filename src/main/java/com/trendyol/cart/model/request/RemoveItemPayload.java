package com.trendyol.cart.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveItemPayload extends CommandPayload {
    private int itemId;

    @Override
    public void validate() {
        if (itemId <= 0) {
            throw new IllegalArgumentException("Item ID must be greater than 0.");
        }
    }
}
