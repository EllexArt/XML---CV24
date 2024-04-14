package fr.univrouen.cv24.configuration;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAdapter extends XmlAdapter<String, LocalDate> {

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_LOCAL_DATE;
    // Unmarshal by converting the value type to a bound type.
    @Override
    public LocalDate unmarshal(String v) {
        return LocalDate.parse(v, dateFormat);
    }

    // Marshal by converting the bound type to a value type.
    @Override
    public String marshal(LocalDate v) {
        return v.format(dateFormat);
    }

}
