package de.grzb.verleihservice.util;

import java.util.*;
import javax.persistence.*;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LongListConverter implements AttributeConverter<List<Long>, String> {

    @Override
    public String convertToDatabaseColumn(List<Long> list) {
        List<String> stringList = new ArrayList<>();
        for (Long longValue : list) {
            stringList.add(longValue.toString());
        }
        return String.join(",", stringList);
    }

    @Override
    public List<Long> convertToEntityAttribute(String joined) {
        List<String> stringList = new ArrayList<>(Arrays.asList(joined.split(",")));
        List<Long> result = new ArrayList<>();
        for (String string : stringList) {
            result.add(Long.valueOf(string));
        }
        return result;
    }
}
