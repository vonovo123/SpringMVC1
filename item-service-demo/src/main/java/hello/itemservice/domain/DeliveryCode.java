package hello.itemservice.domain;

/*
* FAST :  빠른배송
* NORMAL: 일반배송
* SLOW: 느린배송
* */
public enum  DeliveryCode {
    FAST("빠른배송"),NORMAL("일반배송"),SLOW("느린배송");

    private final String description;
    public String getDescription() {return description;}
    DeliveryCode(String description) {
        this.description = description;
    }
}
