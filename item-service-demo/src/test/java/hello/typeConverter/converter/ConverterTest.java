package hello.typeConverter.converter;

import hello.typeConverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    @Test
    void StringToIntegerConvert() {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        assertThat(converter.convert("123")).isEqualTo(123);
    }

    @Test
    void IntegerToStringConvert() {
        IntegerToStringConverter converter = new IntegerToStringConverter();
        assertThat(converter.convert(123)).isEqualTo("123");
    }

    @Test
    void StringToIpPortConvert() {
        StringToIpPortConverter converter = new StringToIpPortConverter();
        assertThat(converter.convert("127.0.0.1:8080")).isEqualTo(new IpPort("127.0.0.1", 8080));
    }
    @Test
    void IpPortToStringConvert() {
        IpPortToStringConverter converter = new IpPortToStringConverter();
        assertThat(converter.convert(new IpPort("127.0.0.1", 8080))).isEqualTo("127.0.0.1:8080");
    }
}