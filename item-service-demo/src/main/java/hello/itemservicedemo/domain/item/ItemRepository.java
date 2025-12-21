package hello.itemservicedemo.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    private static final Map<Long,Item> store = new HashMap<>();
    private static Long sequence = 0L;

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(),item);
        return store.get(item.getId());
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(Long id,Item item){
        Item find = findById(id);
        find.setItemName(item.getItemName());
        find.setPrice(item.getPrice());
        find.setQuantity(item.getQuantity());
        find.setOpen(item.getOpen());
        find.setItemType(item.getItemType());
        find.setRegions(item.getRegions());
        find.setDeliveryCode(item.getDeliveryCode());
    }

    public void clear(){
        store.clear();
    }
}
