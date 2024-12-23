package com.trendyol.cart.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.trendyol.cart.command.execute.ResetCartCommand;
import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.item.DefaultItem;
import com.trendyol.cart.model.response.CommandResult;
import com.trendyol.cart.store.CartObserverManager;
import com.trendyol.cart.store.CartStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ResetCartCommandTest {
    @Mock
    private CartStore cartStore;

    @InjectMocks
    private ResetCartCommand resetCartCommand;

    @Test
    void execute_shouldResetCartSuccessfully() {
        var cart = new Cart(new CartObserverManager());
        cart.addItem(new DefaultItem(1, 1001, 2001, 1000.0, 1));
        cart.addItem(new DefaultItem(2, 1001, 2001, 1000.0, 1));

        when(cartStore.getCart()).thenReturn(cart);

        CommandResult result = resetCartCommand.execute(null);

        assertThat(cart.getItems().size()).isEqualTo(0);
        assertThat(result.isResult()).isTrue();
        assertThat(result.getMessage()).isEqualTo("Cart has been reset successfully");
    }
}
