package com.trendyol.cart.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendyol.cart.command.execute.DisplayCartCommand;
import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.item.DefaultItem;
import com.trendyol.cart.model.request.DisplayCartPayload;
import com.trendyol.cart.model.response.CommandResult;
import com.trendyol.cart.store.CartObserverManager;
import com.trendyol.cart.store.CartStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DisplayCartCommandTest {

    @Mock
    private CartStore cartStore;

    @InjectMocks
    private DisplayCartCommand displayCartCommand;

    @Test
    void execute_shouldReturnCartAsJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Cart cart = new Cart(new CartObserverManager());
        cart.addItem(new DefaultItem(1, 1001, 2001, 100.0, 2));

        when(cartStore.getCart()).thenReturn(cart);
        CommandResult result = displayCartCommand.execute(new DisplayCartPayload());

        assertThat(result.isResult()).isTrue();
        assertThat(objectMapper.writeValueAsString(result.getMessage()))
                .isEqualTo(
                        "{\"items\":[{\"itemId\":1,\"price\":100.0,\"quantity\":2,\"categoryId\":1001,\"sellerId\":2001,\"vasItems\":[]}],\"totalDiscount\":0.0,\"totalAmount\":0.0,\"appliedPromotionId\":null}");
        verify(cartStore, times(1)).getCart();
    }
}
