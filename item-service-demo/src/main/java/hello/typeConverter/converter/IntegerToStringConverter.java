package hello.typeConverter.converter;

import org.jspecify.annotations.Nullable;
import org.springframework.core.convert.converter.Converter;

public class IntegerToStringConverter implements Converter<Integer, String> {

    @Override
    public @Nullable String convert(Integer source) {
        return String.valueOf(source);
    }
}
