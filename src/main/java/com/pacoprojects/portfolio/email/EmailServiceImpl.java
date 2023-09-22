package com.pacoprojects.portfolio.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    private static final String EMAIL_ERROR_MESSAGE = "Failed to send Email";
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    @Async
    public void sendMail(EmailObject emailObject) {

        try {
            // Creating a simple mail message
            SimpleMailMessage simpleMailMessage = getSimpleMailMessage(emailObject);

            // Sending the mail
            javaMailSender.send(simpleMailMessage);

            // Metodo deve ser VOID para conseguir funcionar de modo Asyncrono
        } catch (Exception exception) {
            LOGGER.error(EMAIL_ERROR_MESSAGE, exception);
            throw new IllegalStateException(EMAIL_ERROR_MESSAGE);
        }
    }

    @Override
    @Async
    public void sendMailWithAttachment(EmailObject emailObject) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            // Setting multipart as true for attachments to be send
            MimeMessageHelper helper = getMimeMessageHelper(emailObject, mimeMessage);

            // Adding the attachment
            if (emailObject.attachment() != null && !emailObject.attachment().isBlank()) {
                FileSystemResource file = new FileSystemResource(emailObject.attachment());
                helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            }

            // Sending the mail
            javaMailSender.send(mimeMessage);

            // Metodo deve ser VOID para conseguir funcionar de modo Asyncrono
        } catch (MessagingException exception) {
            LOGGER.error(EMAIL_ERROR_MESSAGE, exception);
            throw new IllegalStateException(EMAIL_ERROR_MESSAGE);
        }
    }

    private SimpleMailMessage getSimpleMailMessage(EmailObject emailObject) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        // Setting up necessary details
        // Remetente:
        simpleMailMessage.setFrom(sender);

        // Destinatario
        simpleMailMessage.setTo(emailObject.recipient());

        // Assunto do Email
        simpleMailMessage.setSubject(emailObject.subject());

        // Corpo do Email
        simpleMailMessage.setText(emailObject.message());
        return simpleMailMessage;
    }

    private MimeMessageHelper getMimeMessageHelper(EmailObject emailObject, MimeMessage mimeMessage) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

        // Setting up necessary details
        // Remetente:
        helper.setFrom(sender);

        // Destinatario
        helper.setTo(emailObject.recipient());

        // Assunto do Email
        helper.setSubject(emailObject.subject());

        // Corpo do Email
        helper.setText(emailObject.message(), true);
        return helper;
    }
}
