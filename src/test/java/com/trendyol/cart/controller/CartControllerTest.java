package com.trendyol.cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.trendyol.cart.command.execute.Command;
import com.trendyol.cart.command.execute.DisplayCartCommand;
import com.trendyol.cart.model.request.CommandPayload;
import com.trendyol.cart.model.request.CommandRequest;
import com.trendyol.cart.model.request.DisplayCartPayload;
import com.trendyol.cart.model.response.CommandResult;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private List<Command> commands;

    @Mock
    private DisplayCartCommand command;

    @InjectMocks
    private CartController cartController;

    @Test
    void executeCommand_shouldCallCorrectCommand() {
        CommandRequest request = new CommandRequest("displayCart", mock(DisplayCartPayload.class));
        CommandResult expectedResult = new CommandResult(true, "Executed successfully");

        when(command.execute(any())).thenReturn(expectedResult);

        CartController controller = new CartController(List.of(command));

        CommandResult result = controller.executeCommand(request);

        assertThat(result).isNotNull();
        assertThat(result.isResult()).isTrue();
        assertThat(result.getMessage()).isEqualTo("Executed successfully");
    }

    @Test
    void executeCommand_shouldThrowExceptionForInvalidCommand() {
        CommandRequest request = new CommandRequest("invalidCommand", mock(CommandPayload.class));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> cartController.executeCommand(request));

        assertThat(exception.getMessage()).isEqualTo("Invalid command: invalidCommand");
    }
}
