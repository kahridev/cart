package com.trendyol.cart.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisplayCartPayload extends CommandPayload {

    @Override
    public void validate() {}
}
