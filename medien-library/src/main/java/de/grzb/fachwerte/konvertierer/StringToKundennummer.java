package de.grzb.fachwerte.konvertierer;

import org.springframework.core.convert.converter.Converter;

import de.grzb.fachwerte.Kundennummer;

public class StringToKundennummer implements Converter<String, Kundennummer> {

    @Override
    public Kundennummer convert(String source) {
        return new Kundennummer(Integer.parseInt(source));
    }

}
