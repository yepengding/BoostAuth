package org.veritasopher.boostauth.service;

/**
 * Email Service
 *
 * @author Yepeng Ding
 */
public interface EmailService {

    /**
     * Send an email from a given authenticated sender to a recipient
     *
     * @param sender    sender
     * @param password  sender password
     * @param recipient recipient
     * @param subject   subject
     * @param content   content
     */
    void sendEmail(String sender, String password, String recipient, String subject, String content);

}
