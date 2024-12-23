package com.trendyol.cart.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendyol.cart.model.request.AddItemPayload;
import com.trendyol.cart.model.request.AddVasItemPayload;
import com.trendyol.cart.model.request.CommandPayload;
import com.trendyol.cart.model.request.CommandRequest;
import com.trendyol.cart.model.request.RemoveItemPayload;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandRequestDeserializer extends JsonDeserializer<CommandRequest> {

    private final ObjectMapper objectMapper;

    @Override
    public CommandRequest deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        String command = node.get("command").asText();

        CommandRequest request = new CommandRequest();
        request.setCommand(command);
        request.setPayload(getPayload(node, command));
        return request;
    }

    private Class<? extends CommandPayload> determinePayloadType(String command) {
        switch (command) {
            case "addItem":
                return AddItemPayload.class;
            case "removeItem":
                return RemoveItemPayload.class;
            case "addVasItemToItem":
                return AddVasItemPayload.class;
            default:
                throw new IllegalArgumentException("Unsupported command: " + command);
        }
    }

    private CommandPayload getPayload(JsonNode node, String command)
            throws JsonProcessingException, IllegalArgumentException {
        JsonNode payloadNode = node.get("payload");
        if (payloadNode == null) {
            return null;
        }
        Class<? extends CommandPayload> payloadType = determinePayloadType(command);
        return objectMapper.treeToValue(payloadNode, payloadType);
    }
}
