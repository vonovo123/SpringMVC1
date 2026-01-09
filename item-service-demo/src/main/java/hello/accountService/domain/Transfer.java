package hello.accountService.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Transfer {

    @NotEmpty
    private String receiveMemberName;
    @Size(min = 14, max = 14,message = "계좌번호는 정확히 14자리여야 합니다.")
    private String receiveAcctNo;
    @NotEmpty String receiveAcctBankCD;
    @NotEmpty
    private int amount;
}
