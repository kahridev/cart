package com.trendyol.cart.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.trendyol.cart.config.CommandRequestDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = CommandRequestDeserializer.class)
public class CommandRequest {
    private String command;
    private CommandPayload payload;
}
