package com.ml.test.mutant.detector.domain;

import com.ml.test.mutant.detector.util.MutantConstants;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class AdnConverter implements AttributeConverter<String[], String> {

    @Override
    public String convertToDatabaseColumn(final String[] attribute) {
        final Optional<String> stringFormatted = Arrays.stream(attribute).reduce((current, next)-> current.concat(MutantConstants.ADN_DATABASE_SEPARATOR).concat(next));
        return stringFormatted.orElse(null);
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        return dbData.split(MutantConstants.ADN_DATABASE_SEPARATOR);
    }
}
