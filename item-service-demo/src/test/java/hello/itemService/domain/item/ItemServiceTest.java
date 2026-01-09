package hello.itemService.domain.item;


import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import hello.itemservice.domain.ItemService;
import hello.itemservice.domain.DeliveryCode;
import hello.itemservice.domain.ItemType;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

@SpringBootTest
@Slf4j
public class ItemServiceTest {
    @Autowired
    ItemService itemService;
    @TestConfiguration
    static class TestConfig {
        DataSource dataSource;

        public TestConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Bean
        PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
        @Bean
        ItemService itemService(){
            return new ItemService(itemRepository());
        }
        @Bean
        ItemRepository itemRepository() {
            return new ItemRepository(dataSource);
        }
    }

    @Test
    @DisplayName("상품등록&조회&삭제")
    void saveItem() throws SQLException {
        ArrayList<String> regions = new ArrayList<>();
        regions.add("부산");
        regions.add("서울");
        Item item = new Item("item1", 1000, 10,true, regions , ItemType.ETC, DeliveryCode.SLOW);
        Item savedItem = itemService.saveItem(item);
        Item findItem = itemService.findItemById(savedItem.getId());
        Assertions.assertThat(savedItem.getItemName()).isEqualTo(findItem.getItemName());
        itemService.removeItemById(savedItem.getId());
    }

    @Test
    @DisplayName("등록&업데이터&조회&삭제")
    void updateItem(){
        ArrayList<String> regions = new ArrayList<>();
        regions.add("부산");
        regions.add("서울");
        Item item = new Item("item1", 1000, 10,true, regions , ItemType.ETC, DeliveryCode.SLOW);
        Item savedItem = itemService.saveItem(item);
        regions = new ArrayList<>();
        regions.add("대전");
        regions.add("김해");
        Item updateItem = new Item("item2", 2000, 20,false, regions , ItemType.BOOK, DeliveryCode.NORMAL);
        itemService.updateItem(savedItem.getId(),updateItem);
        Item updatedItem = itemService.findItemById(savedItem.getId());
        Assertions.assertThat(updatedItem.getItemName()).isEqualTo(updatedItem.getItemName());
        itemService.removeItemById(savedItem.getId());
    }
}
