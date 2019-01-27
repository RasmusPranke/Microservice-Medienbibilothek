package de.grzb.fachwerte.konvertierer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import de.grzb.fachwerte.Kundennummer;

@Configuration
public class ConverterConfiguration {
    @Bean
    public Converter<String, Kundennummer> stringToKundennummerConverter() {
        return new StringToKundennummer();
    }
}
