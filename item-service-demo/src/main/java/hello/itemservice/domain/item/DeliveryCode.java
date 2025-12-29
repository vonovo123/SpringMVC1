package hello.itemservice.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
/*
* FAST :  빠른배송
* NORMAL: 일반배송
* SLOW: 느린배송
* */
public class DeliveryCode {
    private String code;
    private String displayName;
}
