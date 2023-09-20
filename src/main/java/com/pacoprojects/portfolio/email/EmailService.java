package com.pacoprojects.portfolio.email;

public interface EmailService {
        void sendMail(EmailObject emailObject);
        void sendMailWithAttachment(EmailObject emailObject);
}
