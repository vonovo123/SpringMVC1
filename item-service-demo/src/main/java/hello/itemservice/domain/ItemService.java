package hello.itemservice.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository repository;

    @Transactional(rollbackFor = Exception.class)
    public Item saveItem(Item item) {
        Item savedItem = repository.insertItem(item);
        repository.insertItemRegionById(savedItem.getId(),savedItem);
        return savedItem;
    }
    @Transactional(rollbackFor = Exception.class)
    public void updateItem(String id, Item updateItem) {
        Item findItem = repository.selectItemById(id);
        repository.updateItem(id,findItem,updateItem);
        repository.deleteItemRegionById(id);
        repository.insertItemRegionById(id,updateItem);
    }

    public List<Item> findAll(){
        return repository.selectAll();
    }

    public Item findItemById(String id){
        Item findItem = repository.selectItemById(id);
        ArrayList<String> regions = repository.selectItemRegionById(id);
        findItem.setRegions(regions);
        return findItem;
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeItemById(String id){
        repository.deleteItemById(id);
        repository.deleteItemRegionById(id);
    }

}
