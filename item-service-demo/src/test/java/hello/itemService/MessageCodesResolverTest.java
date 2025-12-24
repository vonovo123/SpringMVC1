package hello.itemService;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

public class MessageCodesResolverTest {
    MessageCodesResolver codeResolver = new DefaultMessageCodesResolver();

    @Test
    public void resolveMessageCodes(){
        String[] strings = codeResolver.resolveMessageCodes("required", "item");
        Assertions.assertThat(strings).containsExactly("required.item", "required");
    }

    @Test
    public void messagesCodesResolverField(){
        String[] strings = codeResolver.resolveMessageCodes("required", "item","itemName", String.class);
        Assertions.assertThat(strings).containsExactly("required.item.itemName", "required.itemName","required.java.lang.String","required");
    }
}
