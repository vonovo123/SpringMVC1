package hello.membeservice.domain;

public enum BankCode {
    하나은행("82"),우리은행("80"),국민은행("83"),신한은행("81");

    private final String description;
    public String getDescription() {return description;}
    BankCode(String description) {
        this.description = description;
    }
}
