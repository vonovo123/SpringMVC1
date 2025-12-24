package hello.itemService.domain.item;

import hello.itemservicedemo.domain.item.Item;
import hello.itemservicedemo.domain.item.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository repository= new ItemRepository();
    @AfterEach
    void clear(){
        repository.clear();
    }

    @Test
    void save(){
        //given
        Item item = new Item("item1", 1000, 10);
        //when
        Item saved = repository.save(item);
        //then
        Item find = repository.findById(saved.getId());
        assertThat(find).isEqualTo(saved);
    }

    @Test
    void findAll(){
        //given
        Item item1 = new Item("item1", 1000, 10);
        Item item2 = new Item("item2", 1000, 10);
        repository.save(item1);
        repository.save(item2);
        List<Item> items = repository.findAll();
        assertThat(items.size()).isEqualTo(2);
        assertThat(items).contains(item1,item2);
    }

    @Test
    void updateItem(){
        //given
        Item item1 = new Item("item1", 1000, 10);
        Item save = repository.save(item1);
        Long saveId = save.getId();
        //when
        Item update = new Item("item2", 2000, 20);
        repository.update(saveId,update);
        Item find = repository.findById(saveId);

        //then
        assertThat(find.getItemName()).isEqualTo(update.getItemName());
        assertThat(find.getPrice()).isEqualTo(update.getPrice());
        assertThat(find.getQuantity()).isEqualTo(update.getQuantity());
    }


}