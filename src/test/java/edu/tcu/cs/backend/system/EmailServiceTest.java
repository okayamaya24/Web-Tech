package edu.tcu.cs.backend.system;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void testSendSimpleMessage() {
        // Set up test data
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test Body";
        
        // Set the from email via reflection
        ReflectionTestUtils.setField(emailService, "fromEmail", "from@example.com");
        
        // Execute the method
        emailService.sendSimpleMessage(to, subject, text);
        
        // Verify that the email sender was called with the correct message
        verify(emailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testSendHtmlMessage() throws MessagingException {
        // Set up test data
        String to = "test@example.com";
        String subject = "Test HTML Subject";
        String htmlContent = "<html><body><h1>Test</h1></body></html>";
        
        // Set the from email via reflection
        ReflectionTestUtils.setField(emailService, "fromEmail", "from@example.com");
        
        // Mock the creation of a MimeMessage
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        
        // Execute the method
        emailService.sendHtmlMessage(to, subject, htmlContent);
        
        // Verify that the email sender was called with the correct message
        verify(emailSender).send(mimeMessage);
    }

    @Test
    public void testSendInvitationEmail() throws MessagingException {
        // Set up test data
        String to = "newcrew@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String temporaryPassword = "temp123";
        
        // Set the from email via reflection
        ReflectionTestUtils.setField(emailService, "fromEmail", "from@example.com");
        
        // Mock the creation of a MimeMessage
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        
        // Execute the method
        emailService.sendInvitationEmail(to, firstName, lastName, temporaryPassword);
        
        // Verify that the email sender was called with the correct message
        verify(emailSender).send(mimeMessage);
    }
} 