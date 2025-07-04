package com.schedule.eventz.service;

import com.schedule.eventz.models.Event;
import com.schedule.eventz.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.email.enabled:false}")
    private boolean emailEnabled;

    public EmailService() {
        this.mailSender = null;
    }

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void sendEmail(String to, String subject, String content) {
        if (emailEnabled) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setTo(to);
                message.setSubject(subject);
                message.setText(content);

                mailSender.send(message);
                log.info("Email sent successfully to: {}", to);
            } catch (Exception e) {
                log.error("Failed to send email to: {}. Error: {}", to, e.getMessage());
            }
        } else {
            log.info("=== EMAIL WOULD BE SENT ===");
            log.info("To: {}", to);
            log.info("Subject: {}", subject);
            log.info("Content:\n{}", content);
            log.info("=========================");
        }
    }

    public void sendRegistrationConfirmation(User user, Event event, String status) {
        String subject = "Registration " + status + " - " + event.getName();
        String content = buildRegistrationEmail(user, event, status);
        sendEmail(user.getEmail(), subject, content);
    }

    public void sendWaitlistPromotion(User user, Event event) {
        String subject = "You're confirmed! - " + event.getName();
        String content = buildWaitlistPromotionEmail(user, event);
        sendEmail(user.getEmail(), subject, content);
    }

    public void sendWelcomeEmail(User user) {
        String subject = "Welcome to EventZ!";
        String content = buildWelcomeEmail(user);
        sendEmail(user.getEmail(), subject, content);
    }

    private String buildRegistrationEmail(User user, Event event, String status) {
        return String.format("""
        Hi %s,
        
        Your registration for "%s" has been %s.
        
        Event Details:
        - Date: %s
        - Location: %s
        - Status: %s
        
        Thank you for using EventZ!
        
        Best regards,
        EventZ Team
        """,
                user.getFirstName(),
                event.getName(),
                status.toLowerCase(),
                event.getStartTime(),
                event.getLocation(),
                status
        );
    }

    private String buildWaitlistPromotionEmail(User user, Event event) {
        return String.format("""
        Great news %s!
        
        A spot has opened up for "%s" and you've been moved from the waitlist to CONFIRMED!
        
        Event Details:
        - Date: %s
        - Location: %s
        
        We look forward to seeing you there!
        
        Best regards,
        EventZ Team
        """,
                user.getFirstName(),
                event.getName(),
                event.getStartTime(),
                event.getLocation()
        );
    }

    private String buildWelcomeEmail(User user) {
        return String.format("""
        Welcome to EventZ, %s!
        
        Your account has been successfully created. You can now:
        - Browse and search for events
        - Register for events
        - Manage your registrations
        
        Thank you for joining EventZ!
        
        Best regards,
        EventZ Team
        """,
                user.getFirstName()
        );
    }
}
