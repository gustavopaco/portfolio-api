package com.pacoprojects.portfolio.constants;

public class Messages {
    private Messages() {}

    /*NotFound Messages*/
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_ROLE_NOT_FOUND = "User role not found: ";
    public static final String PROJECT_NOT_FOUND = "Project not found: ";
    public static final String SKILL_NOT_FOUND = "Skill not found";
    public static final String BIO_NOT_FOUND = "Bio not found";
    public static final String SOCIAL_NOT_FOUND = "Social not found";
    public static final String COURSE_NOT_FOUND = "Course not found: ";
    public static final String CERTIFICATION_NOT_FOUND = "Certification not found: ";
    public static final String CURRICULUM_NOT_FOUND = "Curriculum not found: ";

    /* Bad Request Messages*/
    public static final String INVALID_REQUEST_BODY = "Request body is invalid: ";
    /*Jwt Token Messages*/
    public static final String TOKEN_EXPIRED = "Token expired";
    public static final String INVALID_TOKEN = "Invalid token";

    /*Authentication Messages*/
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String USER_NOT_ACTIVE = "User not active";

    /*Internal Messages*/
    public static final String INTERNAL_ERROR = "Unexpected error on server";
    public static final String INTERNAL_DATA_INTEGRITY_ERROR = "Data integrity error: ";
    public static final String INTERNAL_SQL_ERROR = "SQL error: ";
    public static final String INTERNAL_CONSTRAINT_VIOLATION_ERROR = "Constraint violation error: ";

    /*Registration*/
    public static final String THANK_YOU_FOR_REGISTERING = "Thank you for registering!";

    /*URL Password Recovery*/
    private static final String PASSWORD_RECOVERY_URL = "https://pacoprojects.com/password-recovery?token=";
    public static final String PASSWORD_RECOVERY_TITLE = "Password Recovery";
    public static String getRecoveryUrl(String token) {
        return PASSWORD_RECOVERY_URL.concat(token);
    }
}
