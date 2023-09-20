package com.pacoprojects.portfolio.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleApplication {
    ADMIN("Administrator"),
    USER("User");

    private final String description;

    @Override
    public String toString() {
        return this.description;
    }
}
