package com.pacoprojects.portfolio.service;

import com.pacoprojects.portfolio.dto.ContactRequest;
import com.pacoprojects.portfolio.email.EmailMessage;
import com.pacoprojects.portfolio.email.EmailObject;
import com.pacoprojects.portfolio.email.EmailService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final EmailService emailService;
    private static final String SUBJECT = "New Contact Request";
    @Value("${spring.mail.personal.username}")
    private String recipient;

    public void sendEmail(@NotNull ContactRequest contactRequest) {
        emailService.sendMailWithAttachment(EmailObject.builder()
                        .recipient(recipient)
                        .subject(SUBJECT)
                        .message(EmailMessage.buildMessage(
                                contactRequest.name(),
                                contactRequest.email(),
                                contactRequest.message())
                        )
                .build());
    }
}
