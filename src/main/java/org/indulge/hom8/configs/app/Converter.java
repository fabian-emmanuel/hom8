package org.indulge.hom8.configs.app;

import org.indulge.hom8.enums.Preference;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public record Converter() {
    @WritingConverter
    public static class PreferenceWritingConverter implements org.springframework.core.convert.converter.Converter<Set<Preference>, String> {
        @Override
        public String convert(Set<Preference> preferences) {
            return preferences.stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(","));
        }
    }

    @ReadingConverter
    public static class PreferenceReadingConverter implements org.springframework.core.convert.converter.Converter<String, Set<Preference>> {
        @Override
        public Set<Preference> convert(String source) {
            return Arrays.stream(source.split(","))
                    .map(Preference::valueOf)
                    .collect(Collectors.toSet());
        }
    }
}
