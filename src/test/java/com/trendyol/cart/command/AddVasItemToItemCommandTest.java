package com.trendyol.cart.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.trendyol.cart.command.execute.AddVasItemToItemCommand;
import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.item.DefaultItem;
import com.trendyol.cart.model.request.AddVasItemToItemPayload;
import com.trendyol.cart.model.response.CommandResult;
import com.trendyol.cart.store.CartObserverManager;
import com.trendyol.cart.store.CartStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddVasItemToItemCommandTest {

    @Mock
    private CartStore cartStore;

    @InjectMocks
    private AddVasItemToItemCommand addVasItemCommand;

    @Test
    void execute_shouldAddVasItemSuccessfully() {
        var payload = new AddVasItemToItemPayload();
        payload.setItemId(1);
        payload.setPrice(500.0);
        payload.setQuantity(1);
        payload.setVasCategoryId(3242);
        payload.setVasItemId(2);
        payload.setVasSellerId(5003);

        var cart = new Cart(new CartObserverManager());
        cart.addItem(new DefaultItem(1, 1001, 2001, 1000.0, 1));

        when(cartStore.getCart()).thenReturn(cart);

        CommandResult result = addVasItemCommand.execute(payload);

        assertThat(cart.getItems().size()).isEqualTo(1);
        assertThat(((DefaultItem) cart.getItems().get(0)).getVasItems().size()).isEqualTo(1);
        assertThat(result.isResult()).isTrue();
        assertThat(result.getMessage()).isEqualTo("VasItem added successfully");
    }

    @Test
    void execute_shouldThrowExceptionWhenItemNotFound() {
        var payload = new AddVasItemToItemPayload();
        payload.setItemId(1);
        payload.setPrice(500.0);
        payload.setQuantity(1);
        payload.setVasCategoryId(3242);
        payload.setVasItemId(2);
        payload.setVasSellerId(5003);

        when(cartStore.getCart()).thenReturn(new Cart(new CartObserverManager()));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> addVasItemCommand.execute(payload));

        assertThat(exception.getMessage()).isEqualTo("Item with given id not found!");
    }

    @Test
    void execute_shouldThrowExceptionWhenCategoryNotValid() {
        var payload = new AddVasItemToItemPayload();
        payload.setItemId(1);
        payload.setPrice(500.0);
        payload.setQuantity(1);
        payload.setVasCategoryId(3000);
        payload.setVasItemId(2);
        payload.setVasSellerId(5003);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> addVasItemCommand.execute(payload));

        assertThat(exception.getMessage()).isEqualTo("Invalid VasItem category. Must be 3242.");
    }

    @Test
    void execute_shouldThrowExceptionWhenVasSellerIdNotValid() {
        var payload = new AddVasItemToItemPayload();
        payload.setItemId(1);
        payload.setPrice(500.0);
        payload.setQuantity(1);
        payload.setVasCategoryId(3242);
        payload.setVasItemId(2);
        payload.setVasSellerId(5000);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> addVasItemCommand.execute(payload));

        assertThat(exception.getMessage()).isEqualTo("Invalid VasItem seller. Must be 5003.");
    }
}
