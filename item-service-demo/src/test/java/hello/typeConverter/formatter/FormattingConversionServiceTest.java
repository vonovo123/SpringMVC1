package hello.typeConverter.formatter;

import hello.typeConverter.converter.IpPortToStringConverter;
import hello.typeConverter.converter.StringToIpPortConverter;
import hello.typeConverter.type.IpPort;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.FormattingConversionService;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;


class FormattingConversionServiceTest  {
    @Test
    public void foramattingConversionService() throws ParseException {
        FormattingConversionService service = new FormattingConversionService();

        service.addConverter(new IpPortToStringConverter());
        service.addConverter(new StringToIpPortConverter());
        service.addFormatter(new MyNumberFormatter());

        //컨버터 사용
        IpPort ipPort = service.convert("127.0.0.1:8080",
                IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080)); //포맷터 사용
        assertThat(service.convert(1000,
                String.class)).isEqualTo("1,000");
        assertThat(service.convert("1,000",
                Long.class)).isEqualTo(1000L);
    }
}
