package hello.itemservicedemo.web.validation;

import hello.itemservicedemo.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;
        //item=Item(id=null, itemName=itemC, price=30000, quantity=100, open=false, regions=[], itemType=null, deliveryCode=)
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "required");

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            //br.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if(item.getQuantity() == null || item.getQuantity() > 9999 ){
            //br.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"},new Object[]{9999}, null));
            errors.rejectValue("quantity", "max",new Object[]{9999}, null);
        }
        if(item.getRegions().size() == 0){
            errors.rejectValue("regions", "empty",  null);
        }

        if(item.getItemType() == null){
            errors.rejectValue("itemType", "empty",  null);
        }
        if(item.getDeliveryCode() == null || "".equals(item.getDeliveryCode())){
            errors.rejectValue("deliveryCode", "empty",  null);
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                //br.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000,resultPrice},null));
                errors.reject("totalPriceMin",new Object[]{10000,resultPrice},null);
            }
        }
    }
}
