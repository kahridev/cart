package com.trendyol.cart.controller;

import com.trendyol.cart.command.execute.Command;
import com.trendyol.cart.model.request.CommandRequest;
import com.trendyol.cart.model.response.CommandResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final List<Command> commands;

    @PostMapping("/execute")
    public CommandResult executeCommand(@RequestBody CommandRequest request) {
        var command = commands.stream()
                .filter(c -> c.getClass().getSimpleName().equalsIgnoreCase(request.getCommand() + "Command"))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid command: " + request.getCommand()));

        return command.execute(request.getPayload());
    }
}
