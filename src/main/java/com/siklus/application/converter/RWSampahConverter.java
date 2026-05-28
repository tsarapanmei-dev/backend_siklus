package com.siklus.application.converter;

import com.siklus.application.model.Sampah;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RWSampahConverter
        implements AttributeConverter<Sampah.RWSampah, String> {

    @Override
    public String convertToDatabaseColumn(
            Sampah.RWSampah attribute
    ) {
        return attribute == null
                ? null
                : attribute.getDbValue();
    }

    @Override
    public Sampah.RWSampah convertToEntityAttribute(
            String dbData
    ) {
        return dbData == null
                ? null
                : Sampah.RWSampah.fromDbValue(dbData);
    }
}
