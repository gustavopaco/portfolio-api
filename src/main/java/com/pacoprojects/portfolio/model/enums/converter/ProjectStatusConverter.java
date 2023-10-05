package com.pacoprojects.portfolio.model.enums.converter;

import com.pacoprojects.portfolio.constants.Messages;
import com.pacoprojects.portfolio.model.enums.ProjectStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ProjectStatusConverter implements AttributeConverter<ProjectStatus, String> {

    @Override
    public String convertToDatabaseColumn(ProjectStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDescription();
    }

    @Override
    public ProjectStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Stream.of(ProjectStatus.values())
                .filter(c -> c.getDescription().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(Messages.PROJECT_NOT_FOUND + dbData));
    }
}
