package org.veritasopher.boostauth.config.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Email SMTP Configuration
 *
 * @author Yepeng Ding
 */
@Component
@Getter
public class EmailSMTPConfig {

    @Value("${email.smtp.host}")
    private String host;

    @Value("${email.smtp.port}")
    private String port;

    @Value("${email.smtp.auth}")
    private String auth;

    @Value("${email.smtp.ssl.enable}")
    private String sslEnable;

    @Value("${email.smtp.ssl.protocols}")
    private String sslProtocols;

    @Value("${email.smtp.starttls.enable}")
    private String starttlsEnable;

}
