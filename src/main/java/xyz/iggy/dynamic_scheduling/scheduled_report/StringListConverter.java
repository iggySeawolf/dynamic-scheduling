package xyz.iggy.dynamic_scheduling.scheduled_report;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return attribute != null ? String.join(SPLIT_CHAR, attribute) : "";
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return dbData != null && !dbData.isEmpty()
                ? Arrays.stream(dbData.split(SPLIT_CHAR)).map(String::trim).collect(Collectors.toList())
                : List.of();
    }
}
