package hello.itemservicedemo.web.item;

import hello.itemservicedemo.domain.item.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/items")
public class ValidationItemApiController {
    @PostMapping("/add")
    public Object add(  @RequestBody @Validated ItemSaveForm itemForm, BindingResult br){
        log.info("API Controller Call");
        if(br.hasErrors()){
            log.info("Validation Error");
            return br.getAllErrors();
        }
        log.info("Validation Success");
        return itemForm;
    }
}
