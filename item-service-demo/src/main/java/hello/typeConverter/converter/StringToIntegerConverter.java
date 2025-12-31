package hello.typeConverter.converter;

import org.jspecify.annotations.Nullable;
import org.springframework.core.convert.converter.Converter;

public class StringToIntegerConverter implements Converter<String, Integer> {
    @Override
    public @Nullable Integer convert(String source) {
        return Integer.valueOf(source);
    }
}
