package hello.itemservice.domain;

import lombok.Data;

import java.util.List;

@Data
public class Item {
    private String id;
    private String itemName;
    private Integer price;
    private Integer quantity;
    private Boolean open;
    private List<String> regions;
    private ItemType itemType;
    private DeliveryCode deliveryCode;
    private String registerId;
    private String registerName;
    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity, Boolean open, List<String> regions, ItemType itemType, DeliveryCode deliveryCode) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.open = open;
        this.regions = regions;
        this.itemType = itemType;
        this.deliveryCode = deliveryCode;
    }
}
