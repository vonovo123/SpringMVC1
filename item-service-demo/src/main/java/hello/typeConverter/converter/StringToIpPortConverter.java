package hello.typeConverter.converter;

import hello.typeConverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToIpPortConverter implements Converter<String, IpPort> {
    @Override
    public @Nullable IpPort convert(String source) {
        log.info("convert String source = {}", source);
        String[] strings = source.split(":");

        return new IpPort(strings[0], Integer.parseInt(strings[1]));
    }
}
