package hello.itemService;

import hello.itemservice.domain.Item;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

public class BeanValidationTest {
    @Test
    void beanValidation(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Item item = new Item();
        item.setId(3L);
        item.setItemName("");
        item.setPrice(100);
        item.setQuantity(1);
        item.setOpen(true);
        item.setItemType(null);
        item.setDeliveryCode(null);
        item.setRegions(new ArrayList<String>());
        Set<ConstraintViolation<Item>> validate = validator.validate(item);
        for (ConstraintViolation<Item> itemConstraintViolation : validate) {
            System.out.println("itemConstraintViolation = " + itemConstraintViolation);
            System.out.println("itemConstraintViolation.getMessage() = " + itemConstraintViolation.getMessage());
        }
        
    }
}
