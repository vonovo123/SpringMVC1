package hello.itemservicedemo.web.item;

import hello.itemservicedemo.ItemType;
import hello.itemservicedemo.domain.item.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemRepository itemRepository;
    //private final ItemValidator itemValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        log.info("init binder {}", binder );
        //binder.setValidator(itemValidator);
    }
    @GetMapping
    public String items(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "items/items";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "items/addForm";
    }


    @PostMapping("/add")
    /*POST/Redirect/get*/
    public String addItemV6(@Validated @ModelAttribute("item") ItemSaveForm itemForm, BindingResult br, RedirectAttributes redirectAttributes, Model model) {
        Item item = getItem(itemForm);
        objectValidator(item,br);
        if(br.hasErrors()) {
            return "items/addForm";
        }
        itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";
    }

    private Item getItem(Item itemForm) {
        Item item = new Item();
        item.setItemName(itemForm.getItemName());
        item.setPrice(itemForm.getPrice());
        item.setQuantity(itemForm.getQuantity());
        item.setOpen(itemForm.getOpen());
        item.setItemType(itemForm.getItemType());
        item.setRegions(itemForm.getRegions());
        item.setDeliveryCode(itemForm.getDeliveryCode());
        log.info("item = {}" , item);
        return item;
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        log.info("item ={}", item );
        model.addAttribute("item", item);
        return "items/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "items/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateForm itemForm, BindingResult br, Model model) {
        Item item = getItem(itemForm);
        objectValidator(item,br);
        if(br.hasErrors()) {
            return "items/editForm";
        }
        itemRepository.update(itemId, item);
        return "redirect:/items/{itemId}";
    }

    void objectValidator(Item item, BindingResult br) {
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                //br.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000,resultPrice},null));
                br.reject("totalPriceMin",new Object[]{10000,resultPrice},null);
            }
        }
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

}
