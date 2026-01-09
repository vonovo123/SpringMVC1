package hello.itemservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ItemUpdateForm extends Item {
    @NotNull
    private String id;
    @NotBlank
    private String itemName;
    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;
    @NotNull
    private Integer quantity;
    private Boolean open;
    @NotEmpty
    private List<String> regions;
    @NotNull
    private ItemType itemType;
    @NotNull
    private DeliveryCode deliveryCode;

}
