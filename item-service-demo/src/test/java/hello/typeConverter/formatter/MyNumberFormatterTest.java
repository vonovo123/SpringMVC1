package hello.typeConverter.formatter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class MyNumberFormatterTest {
    MyNumberFormatter formatter = new MyNumberFormatter();

    @Test
    void parse() throws ParseException {
        Number parseNum = formatter.parse("1,000,000", Locale.KOREA);
        Assertions.assertThat(parseNum).isEqualTo(1000000L);
    }

    @Test
    void print() throws ParseException {
        String print = formatter.print(1000, Locale.KOREA);
        Assertions.assertThat(print).isEqualTo("1000");
    }
}