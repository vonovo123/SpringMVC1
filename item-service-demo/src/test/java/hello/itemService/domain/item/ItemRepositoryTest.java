package hello.itemService.domain.item;

import hello.itemservice.domain.ItemType;
import hello.itemservice.domain.DeliveryCode;
import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class ItemRepositoryTest {
    @Autowired
    ItemRepository repository;

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
        ItemRepository itemRepository() {
            return new ItemRepository(dataSource);
        }

    }

    @Test
    void save(){
        repository.deleteAll();
        //given
        ArrayList<String> regions = new ArrayList<>();
        regions.add("부산");
        regions.add("서울");
        Item item = new Item("item1", 1000, 10,true, regions , ItemType.ETC, DeliveryCode.SLOW);
        //when
        Item saved = repository.insertItem(item);
        repository.deleteItemRegionById(saved.getId());
        repository.insertItemRegionById(saved.getId(),saved);
        //then
        Item find = repository.selectItemById(saved.getId());
        ArrayList<String> region = repository.selectItemRegionById(find.getId());
        find.setRegions(region);
        assertThat(find).isEqualTo(saved);
    }

    @Test
    void updateItem(){
        repository.deleteAll();
        //given
        ArrayList<String> regions = new ArrayList<>();
        regions.add("부산");
        regions.add("서울");
        Item item = new Item("item1", 1000, 10,true, regions , ItemType.ETC, DeliveryCode.SLOW);

        Item save = repository.insertItem(item);
        Long saveId = save.getId();
        //when
        regions = new ArrayList<>();
        regions.add("센프");
        regions.add("대구");
        regions.add("베이징");

        Item update = new Item("item2", 2000, 20, false,  regions , ItemType.BOOK, DeliveryCode.FAST);
        repository.updateItem(saveId, save,update);
        repository.deleteItemRegionById(saveId);
        repository.insertItemRegionById(saveId,update);
        Item find = repository.selectItemById(saveId);
        ArrayList<String> regionById = repository.selectItemRegionById(find.getId());
        find.setRegions(regionById);
        //then
        assertThat(find.getItemName()).isEqualTo(update.getItemName());
        assertThat(find.getPrice()).isEqualTo(update.getPrice());
        assertThat(find.getQuantity()).isEqualTo(update.getQuantity());
        assertThat(find.getOpen()).isEqualTo(update.getOpen());
        assertThat(find.getRegions()).isEqualTo(update.getRegions());
        assertThat(find.getItemType()).isEqualTo(update.getItemType());
        assertThat(find.getDeliveryCode()).isEqualTo(update.getDeliveryCode());
    }


}