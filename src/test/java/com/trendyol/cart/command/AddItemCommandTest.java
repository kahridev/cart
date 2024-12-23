package com.trendyol.cart.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.trendyol.cart.command.execute.AddItemCommand;
import com.trendyol.cart.factory.ItemFactory;
import com.trendyol.cart.model.entity.Cart;
import com.trendyol.cart.model.entity.item.DefaultItem;
import com.trendyol.cart.model.entity.promotion.CategoryPromotion;
import com.trendyol.cart.model.entity.promotion.SameSellerPromotion;
import com.trendyol.cart.model.entity.promotion.TotalPricePromotion;
import com.trendyol.cart.model.request.AddItemPayload;
import com.trendyol.cart.store.CartObserverManager;
import com.trendyol.cart.store.CartStore;
import com.trendyol.cart.store.CartTotalsObserver;
import com.trendyol.cart.store.PromotionObserver;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddItemCommandTest {

    @Mock
    private CartStore cartStore;

    @Mock
    private ItemFactory itemFactory;

    @InjectMocks
    private AddItemCommand addItemCommand;

    @Test
    void execute_shouldAddDefaultItemSuccessfully() {
        var payload = new AddItemPayload(1, 1001, 2001, 100.0, 2);
        var item = new DefaultItem(1, 1001, 2001, 100.0, 2);
        var cartObserverManager = new CartObserverManager();
        cartObserverManager.addObserver(new CartTotalsObserver());
        cartObserverManager.addObserver(new PromotionObserver(
                List.of(new CategoryPromotion(), new SameSellerPromotion(), new TotalPricePromotion())));

        when(itemFactory.createItem(
                        payload.getCategoryId(),
                        payload.getItemId(),
                        payload.getSellerId(),
                        payload.getPrice(),
                        payload.getQuantity()))
                .thenReturn(item);

        when(cartStore.getCart()).thenReturn(new Cart(cartObserverManager));

        var result = addItemCommand.execute(payload);

        verify(itemFactory, times(1)).createItem(anyInt(), anyInt(), anyInt(), anyDouble(), anyInt());
        assertThat(cartStore.getCart().getItems().size()).isEqualTo(1);
        assertThat(result.isResult()).isTrue();
        assertThat(result.getMessage()).isEqualTo("Item added successfully");
    }

    @Test
    void execute_shouldThrowExceptionIfPriceIsZero() {
        var payload = new AddItemPayload(1, 1001, 2001, -100.0, 2); // Negatif fiyat
        var exception = assertThrows(IllegalArgumentException.class, () -> addItemCommand.execute(payload));

        assertThat(exception.getMessage()).isEqualTo("Price must be greater than 0.");
    }

    @Test
    void execute_shouldThrowExceptionIfCategoryIdIsZero() {
        var payload = new AddItemPayload(1, 0, 2001, 100.0, 2); // Negatif fiyat
        var exception = assertThrows(IllegalArgumentException.class, () -> addItemCommand.execute(payload));

        assertThat(exception.getMessage()).isEqualTo("CategoryId is required and must be positive.");
    }

    @Test
    void execute_shouldThrowExceptionIfQuantityIsZero() {
        var payload = new AddItemPayload(1, 1010, 2001, 100.0, 0);
        var exception = assertThrows(IllegalArgumentException.class, () -> addItemCommand.execute(payload));

        assertThat(exception.getMessage()).isEqualTo("Quantity must be greater than 0.");
    }

    @Test
    void execute_shouldThrowExceptionIfCategoryIsZero() {
        var payload = new AddItemPayload(1, 1010, -1, 100.0, 2);
        var exception = assertThrows(IllegalArgumentException.class, () -> addItemCommand.execute(payload));

        assertThat(exception.getMessage()).isEqualTo("SellerId must be greater than 0.");
    }

    @Test
    void execute_shouldThrowExceptionIfItemIdIsZero() {
        var payload = new AddItemPayload(0, 1010, 2001, 100.0, 2);
        var exception = assertThrows(IllegalArgumentException.class, () -> addItemCommand.execute(payload));

        assertThat(exception.getMessage()).isEqualTo("ItemId must be greater than 0.");
    }
}
