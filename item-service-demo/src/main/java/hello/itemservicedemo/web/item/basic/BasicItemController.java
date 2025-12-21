package hello.itemservicedemo.web.item.basic;

import hello.itemservicedemo.ItemType;
import hello.itemservicedemo.domain.item.DeliveryCode;
import hello.itemservicedemo.domain.item.Item;
import hello.itemservicedemo.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
@Slf4j
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "basic/items/items";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "basic/items/addForm";
    }

    //@PostMapping("/add")
    /*RequestParam 활용*/
    public String addItem(@RequestParam String itemName, @RequestParam int price, @RequestParam Integer quantity, Model model) {
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/items/item";
    }

    //@PostMapping("/add")
    /*@ModelAttribute 활용*/
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);
        return "basic/items/item";
    }

    //@PostMapping("/add")
    /*@ModelAttribute parameter name 생략*/
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/items/item";
    }

    //@PostMapping("/add")
    /*@ModelAttribute 생략*/
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/items/item";
    }

    //@PostMapping("/add")
    /*POST/Redirect/get*/
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    /*POST/Redirect/get*/
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        log.info("item.open = {}", item.getOpen());
        log.info("item.getRegions = {}", item.getRegions());
        log.info("item.itemType = {}", item.getItemType());
        itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        log.info("regions ={}", model.getAttribute("regions") );
        model.addAttribute("item", item);
        return "basic/items/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/items/editForm";
    }
    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, @ModelAttribute Item item, Model model) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    @ModelAttribute("regions")
    public Map<String, String> regions(){
        LinkedHashMap<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }
    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes(){
        return ItemType.values();
    }
    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes(){
        ArrayList<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "보통 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }

    @PostConstruct
    public void init(){
        itemRepository.save(new Item("test1", 1000, 10));
        itemRepository.save(new Item("test2", 2000, 20));
    }


}
