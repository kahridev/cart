package com.trendyol.cart.command.execute;

import com.trendyol.cart.model.request.CommandPayload;
import com.trendyol.cart.model.response.CommandResult;

public interface Command<T extends CommandPayload> {
    CommandResult execute(T payload);
}
