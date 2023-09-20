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
}
