package com.pacoprojects.portfolio.model.converter;

import com.pacoprojects.portfolio.constants.Messages;
import com.pacoprojects.portfolio.model.UserRoleApplication;
import jakarta.persistence.AttributeConverter;

import java.util.stream.Stream;

public class UserRoleApplicationConverter implements AttributeConverter<UserRoleApplication, String> {

    @Override
    public String convertToDatabaseColumn(UserRoleApplication attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDescription();
    }

    @Override
    public UserRoleApplication convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(UserRoleApplication.values())
                .filter(c -> c.getDescription().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(Messages.USER_ROLE_NOT_FOUND + dbData));
    }
}
