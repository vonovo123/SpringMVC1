package hello.itemservice.domain.item;

import hello.itemservice.ItemType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ItemSaveForm extends Item {

    @NotBlank
    private String itemName;
    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;
    @NotNull
    @Max(value = 9999)
    private Integer quantity;
    private Boolean open;
    @NotEmpty
    private List<String> regions;
    @NotNull
    private ItemType itemType;
    @NotBlank
    private String deliveryCode;


}
