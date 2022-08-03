package org.veritasopher.boostauth.service.impl;

import org.springframework.stereotype.Service;
import org.veritasopher.boostauth.config.property.EmailSMTPConfig;
import org.veritasopher.boostauth.core.exception.type.AuthenticationException;
import org.veritasopher.boostauth.service.EmailService;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    @Resource
    private EmailSMTPConfig emailSMTPConfig;

    @Override
    public void sendEmail(String sender, String password, String recipient, String subject, String content) {
        Session session = Session.getInstance(getEmailSMTPProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);

            message.setText(content);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new AuthenticationException("Email sending failed.");
        }

    }

    /**
     * Get email SMTP properties
     *
     * @return properties object
     */
    private Properties getEmailSMTPProperties() {
        Properties prop = new Properties();

        prop.put("mail.smtp.host", emailSMTPConfig.getHost());
        prop.put("mail.smtp.port", emailSMTPConfig.getPort());
        prop.put("mail.smtp.auth", emailSMTPConfig.getAuth());
        prop.put("mail.smtp.ssl.enable", emailSMTPConfig.getSslEnable());
        prop.put("mail.smtp.ssl.protocols", emailSMTPConfig.getSslProtocols());
        prop.put("mail.smtp.starttls.enable", emailSMTPConfig.getStarttlsEnable());

        return prop;
    }

}
