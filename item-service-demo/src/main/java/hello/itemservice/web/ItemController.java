package hello.itemservice.web;

import hello.itemservice.domain.*;
import hello.loginservice.web.argumentResolver.Login;
import hello.membeservice.domain.Member;
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

    private final ItemService itemService;
    //private final ItemValidator itemValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        log.info("init binder {}", binder );
        //binder.setValidator(itemValidator);
    }
    @GetMapping
    public String items(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "items/items";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "items/addForm";
    }


    @PostMapping("/add")
    /*POST/Redirect/get*/
    public String addItem(@Validated @Login Member loginMember, @ModelAttribute("item") ItemSaveForm itemForm, BindingResult br, RedirectAttributes redirectAttributes, Model model) {
        Item item = getItem(loginMember,itemForm);
        objectValidator(item,br);
        if(br.hasErrors()) {
            return "items/addForm";
        }
        itemService.saveItem(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";
    }

    private Item getItem(Member loginMember,Item itemForm) {
        Item item = new Item();
        item.setItemName(itemForm.getItemName());
        item.setPrice(itemForm.getPrice());
        item.setQuantity(itemForm.getQuantity());
        item.setOpen(itemForm.getOpen());
        item.setItemType(itemForm.getItemType());
        item.setRegions(itemForm.getRegions());
        item.setDeliveryCode(itemForm.getDeliveryCode());
        item.setRegisterName(loginMember.getName());
        item.setRegisterId(loginMember.getId());
        return item;
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable String itemId, Model model) {
        Item item = itemService.findItemById(itemId);
        log.info("item ={}", item );
        model.addAttribute("item", item);
        return "items/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable String itemId, Model model) {
        Item item = itemService.findItemById(itemId);
        model.addAttribute("item", item);
        return "items/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editItem(@Login Member loginMember, @PathVariable String itemId, @Validated @ModelAttribute("item") ItemUpdateForm itemForm, BindingResult br, Model model) {
        Item item = getItem(loginMember,itemForm);
        objectValidator(item,br);
        if(br.hasErrors()) {
            return "items/editForm";
        }
        itemService.updateItem(itemId, item);
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
    public DeliveryCode[] deliveryCodes(){return DeliveryCode.values();}


}
