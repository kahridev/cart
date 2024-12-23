package com.trendyol.cart.factory;

import static org.assertj.core.api.Assertions.assertThat;

import com.trendyol.cart.model.entity.item.DefaultItem;
import com.trendyol.cart.model.entity.item.DigitalItem;
import com.trendyol.cart.model.entity.item.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ItemFactoryTest {

    @InjectMocks
    private ItemFactory itemFactory;

    @Test
    void createItem_shouldReturnDigitalItemForDigitalCategory() {
        Item item = itemFactory.createItem(DigitalItem.CATEGORY_ID, 1, 1001, 50.0, 1);

        assertThat(item).isInstanceOf(DigitalItem.class);
        DigitalItem digitalItem = (DigitalItem) item;
        assertThat(digitalItem.getItemId()).isEqualTo(1);
        assertThat(digitalItem.getSellerId()).isEqualTo(1001);
        assertThat(digitalItem.getPrice()).isEqualTo(50.0);
        assertThat(digitalItem.getQuantity()).isEqualTo(1);
    }

    @Test
    void createItem_shouldReturnDefaultItemForOtherCategories() {
        Item item = itemFactory.createItem(1001, 2, 2001, 100.0, 2);

        assertThat(item).isInstanceOf(DefaultItem.class);
        DefaultItem defaultItem = (DefaultItem) item;
        assertThat(defaultItem.getItemId()).isEqualTo(2);
        assertThat(defaultItem.getCategoryId()).isEqualTo(1001);
        assertThat(defaultItem.getSellerId()).isEqualTo(2001);
        assertThat(defaultItem.getPrice()).isEqualTo(100.0);
        assertThat(defaultItem.getQuantity()).isEqualTo(2);
    }
}
