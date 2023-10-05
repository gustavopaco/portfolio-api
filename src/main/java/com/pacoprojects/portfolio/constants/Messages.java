package com.pacoprojects.portfolio.constants;

public class Messages {
    private Messages() {}

    /*NotFound Messages*/
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_ROLE_NOT_FOUND = "User role not found: ";
    public static final String PROJECT_NOT_FOUND = "Project not found: ";
    public static final String SKILL_NOT_FOUND = "Skill not found";

    /*Jwt Token Messages*/
    public static final String TOKEN_EXPIRED = "Token expired";
    public static final String INVALID_TOKEN = "Invalid token";

    /*Authentication Messages*/
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String USER_NOT_ACTIVE = "User not active";

    /*Internal Messages*/
    public static final String INTERNAL_ERROR = "Unexpected error on server";
}
