package com.trendyol.cart;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendyol.cart.model.request.CommandRequest;
import com.trendyol.cart.model.response.CommandResult;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CommandRunner {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public void executeCommands(String filename, String outputFile) {
        try {
            List<CommandRequest> commandRequests =
                    loadAllCommandRequestsFromDirectory("src/main/resources/requests", filename);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                for (CommandRequest request : commandRequests) {
                    CommandResult result = sendCommand(request);

                    writer.write("Request: " + objectMapper.writeValueAsString(request));
                    writer.newLine();
                    writer.write("Response: " + objectMapper.writeValueAsString(result));
                    writer.newLine();

                    CommandRequest displayCartRequest = new CommandRequest("displayCart", null);
                    CommandResult displayResult = sendCommand(displayCartRequest);

                    writer.write("DisplayCart: " + objectMapper.writeValueAsString(displayResult));
                    writer.newLine();
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<CommandRequest> loadAllCommandRequestsFromDirectory(String directoryPath, String fileName)
            throws IOException {
        Path filePath = Paths.get(directoryPath, fileName);

        try {
            String content = Files.readString(filePath);
            var jsonMap = objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {});
            return ((List<Map<String, Object>>) jsonMap.get("requests"))
                    .stream()
                            .map(requestMap -> objectMapper.convertValue(requestMap, CommandRequest.class))
                            .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read or parse JSON file: " + filePath, e);
        }
    }

    private CommandResult sendCommand(CommandRequest request) {
        String url = "http://localhost:8080/cart/execute";
        return restTemplate.postForObject(url, request, CommandResult.class);
    }
}
