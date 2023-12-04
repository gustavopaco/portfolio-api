package com.pacoprojects.portfolio.email;

public class EmailMessage {
    private EmailMessage() {}

    public static String buildMessage(String name, String email, String message) {
        return """
                <div>
                        <h1>You have a new message from your website!</h1>
                        <p>From: %s</p>
                        <p>Email: %s</p>
                        <p>Message: %s</p>
                </div>
                """.formatted(name, email, message);
    }

    public static String buildRegistrationMessage(String email, String password) {
        return """
                <div>
                        <h1>Thank you for registering!</h1>
                        <p>Your email: %s</p>
                        <p>Your temporary password: %s</p>
                </div>
                """.formatted(email, password);
    }

    public static String buildPasswordRecoveryMessage(String url) {
        return """
                <div>
                        <h1>Click on the link below to recover your password</h1>
                        <p>Link: %s</p>
                </div>
                """.formatted(url);
    }
}
