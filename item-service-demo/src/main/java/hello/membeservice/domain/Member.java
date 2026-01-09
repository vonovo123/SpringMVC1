package hello.membeservice.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class Member {
    @Size(min=36, max=36)
    private String  id;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;

    private int point = 0;

    @Size(min = 14, max = 14, message = "계좌번호는 정확히 14자리여야 합니다.")
    private String acctNo;

    @NotEmpty
    private String bankCd;
    public Member() {
    }

    public Member(String loginId, String name, String password, int point, String acctNo, String bankCd) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.point = point;
        this.acctNo = acctNo;
        this.bankCd = bankCd;
    }
}
