package com.trendyol.cart.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.trendyol.cart.command.execute.RemoveItemCommand;
import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.item.DefaultItem;
import com.trendyol.cart.model.request.RemoveItemPayload;
import com.trendyol.cart.model.response.CommandResult;
import com.trendyol.cart.store.CartObserverManager;
import com.trendyol.cart.store.CartStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RemoveItemCommandTest {

    @Mock
    private CartStore cartStore;

    @InjectMocks
    private RemoveItemCommand removeItemCommand;

    @Test
    void execute_shouldRemoveItemSuccessfully() {
        RemoveItemPayload payload = new RemoveItemPayload(1);

        var cart = new Cart(new CartObserverManager());
        cart.addItem(new DefaultItem(1, 1001, 2001, 1000.0, 1));
        cart.addItem(new DefaultItem(2, 1001, 2001, 1000.0, 1));

        when(cartStore.getCart()).thenReturn(cart);

        CommandResult result = removeItemCommand.execute(payload);

        assertThat(cart.getItems().size()).isEqualTo(1);
        assertThat(result.isResult()).isTrue();
        assertThat(result.getMessage()).isEqualTo("Item removed successfully");
    }

    @Test
    void execute_shouldReturnFailureMessageWhenItemNotFound() {
        RemoveItemPayload payload = new RemoveItemPayload(1);
        Cart cart = mock(Cart.class);

        when(cartStore.getCart()).thenReturn(cart);
        when(cart.removeItem(1)).thenReturn(false);

        CommandResult result = removeItemCommand.execute(payload);

        verify(cartStore, times(1)).getCart();
        verify(cart, times(1)).removeItem(1);
        assertThat(result.isResult()).isFalse();
        assertThat(result.getMessage()).isEqualTo("Item not found or failed to remove");
    }

    @Test
    void execute_shouldThrowExceptionForInvalidPayload() {
        RemoveItemPayload payload = new RemoveItemPayload(-1);

        assertThrows(IllegalArgumentException.class, payload::validate);
        verifyNoInteractions(cartStore);
    }
}
