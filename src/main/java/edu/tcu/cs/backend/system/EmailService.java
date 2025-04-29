package edu.tcu.cs.backend.system;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendSimpleMessage(String to, String subject, String text) {
        System.out.println("Sending simple email to: " + to);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            emailSender.send(message);
            System.out.println("Simple email sent successfully to: " + to);
        } catch (Exception e) {
            System.err.println("Failed to send simple email: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void sendHtmlMessage(String to, String subject, String htmlContent) throws MessagingException {
        System.out.println("Sending HTML email to: " + to);
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            
            emailSender.send(message);
            System.out.println("HTML email sent successfully to: " + to);
        } catch (Exception e) {
            System.err.println("Failed to send HTML email: " + e.getMessage());
            e.printStackTrace();
            throw e; // Rethrow to maintain original behavior
        }
    }
    
    public void sendInvitationEmail(String to, String firstName, String lastName, String registrationLink) throws MessagingException {
        System.out.println("Preparing invitation email for: " + to);
        String subject = "Welcome to FrogCrew - Your Invitation";
        
        String htmlContent = """
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { width: 100%%; max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background-color: #4b0082; color: white; padding: 10px 20px; text-align: center; }
                    .content { padding: 20px; }
                    .footer { font-size: 12px; color: #777; text-align: center; margin-top: 20px; }
                    .button { display: inline-block; background-color: #4b0082; color: white; padding: 10px 20px; 
                              text-decoration: none; border-radius: 5px; margin-top: 20px; }
                    .important { font-weight: bold; color: #4b0082; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h2>FrogCrew Invitation</h2>
                    </div>
                    <div class="content">
                        <p>Dear %s %s,</p>
                        
                        <p>You have been invited to join FrogCrew as a crew member. We're excited to have you on board!</p>
                        
                        <p>To complete your registration, please click the button below or use the link provided:</p>
                        
                        <p class="important">This invitation link will expire in 7 days.</p>
                        
                        <a href="%s" class="button">Complete Your Registration</a>
                        
                        <p style="margin-top: 20px;">If the button doesn't work, you can copy and paste this URL into your browser:</p>
                        <p style="word-break: break-all;"><small>%s</small></p>
                        
                        <p>If you have any questions or need assistance, please don't hesitate to contact the admin.</p>
                        
                        <p>Welcome to the team!</p>
                        <p>FrogCrew Administration</p>
                    </div>
                    <div class="footer">
                        <p>This is an automated message. Please do not reply to this email.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(firstName, lastName, registrationLink, registrationLink);
        
        System.out.println("Invitation email content prepared, sending now...");
        sendHtmlMessage(to, subject, htmlContent);
    }
} 