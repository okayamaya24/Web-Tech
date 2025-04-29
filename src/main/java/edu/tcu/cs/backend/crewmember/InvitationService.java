package edu.tcu.cs.backend.crewmember;

import edu.tcu.cs.backend.crewmember.dto.InvitationDto;
import edu.tcu.cs.backend.system.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final EmailService emailService;

    @Value("${app.frontend.url:http://localhost:5173}")
    private String frontendUrl;

    @Autowired
    public InvitationService(InvitationRepository invitationRepository, 
                             CrewMemberRepository crewMemberRepository,
                             EmailService emailService) {
        this.invitationRepository = invitationRepository;
        this.crewMemberRepository = crewMemberRepository;
        this.emailService = emailService;
    }
    
    /**
     * Creates a new invitation and sends an email to the invitee
     */
    public Invitation createInvitation(InvitationDto invitationDto) {
        // Validate inputs
        if (invitationDto == null || invitationDto.getEmail() == null || invitationDto.getEmail().trim().isEmpty() ||
            invitationDto.getFirstName() == null || invitationDto.getFirstName().trim().isEmpty() ||
            invitationDto.getLastName() == null || invitationDto.getLastName().trim().isEmpty() ||
            invitationDto.getQualifiedPosition() == null || invitationDto.getQualifiedPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("All invitation fields (email, firstName, lastName, qualifiedPosition) are required");
        }
        
        // Check email format
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!invitationDto.getEmail().matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        // Check if crew member with this email already exists
        if (crewMemberRepository.existsByEmail(invitationDto.getEmail())) {
            throw new RuntimeException("Crew member with this email already exists");
        }
        
        // Check if invitation with this email already exists
        Optional<Invitation> existingInvitation = invitationRepository.findByEmail(invitationDto.getEmail());
        if (existingInvitation.isPresent()) {
            Invitation invitation = existingInvitation.get();
            if (!invitation.isAccepted() && !invitation.isExpired()) {
                // Refresh the token and update the invitation
                invitation.refreshToken();
                invitation = invitationRepository.save(invitation);
                
                // Send a new invitation email
                sendInvitationEmail(invitation);
                return invitation;
            } else if (invitation.isExpired()) {
                // If expired, delete the old invitation and create a new one
                invitationRepository.delete(invitation);
            } else if (invitation.isAccepted()) {
                throw new RuntimeException("This email has already been invited and has accepted the invitation");
            }
        }
        
        // Create a new invitation
        Invitation invitation = new Invitation(
            invitationDto.getEmail(),
            invitationDto.getFirstName(),
            invitationDto.getLastName(),
            invitationDto.getQualifiedPosition()
        );
        
        // Save the invitation
        invitation = invitationRepository.save(invitation);
        
        // Send invitation email
        sendInvitationEmail(invitation);
        
        return invitation;
    }
    
    /**
     * Sends an invitation email with the registration link
     */
    private void sendInvitationEmail(Invitation invitation) {
        try {
            String registrationLink = frontendUrl + "/register?token=" + invitation.getToken();
            
            emailService.sendInvitationEmail(
                invitation.getEmail(),
                invitation.getFirstName(),
                invitation.getLastName(),
                registrationLink
            );
            
            System.out.println("Invitation email sent to " + invitation.getEmail() + " with token: " + invitation.getToken());
        } catch (MessagingException e) {
            System.err.println("Failed to send invitation email: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Validates an invitation token and returns the invitation if valid
     */
    public Invitation validateInvitationToken(String token) {
        Invitation invitation = invitationRepository.findByToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid invitation token"));
        
        if (invitation.isExpired()) {
            throw new RuntimeException("This invitation has expired");
        }
        
        if (invitation.isAccepted()) {
            throw new RuntimeException("This invitation has already been used");
        }
        
        return invitation;
    }
    
    /**
     * Marks an invitation as accepted
     */
    public void markInvitationAsAccepted(String token) {
        Invitation invitation = validateInvitationToken(token);
        invitation.markAsAccepted();
        invitationRepository.save(invitation);
    }
    
    /**
     * Retrieves all pending invitations
     */
    public List<Invitation> getPendingInvitations() {
        return invitationRepository.findByExpiresAtAfterAndAcceptedFalse(LocalDateTime.now());
    }
    
    /**
     * Deletes an invitation by ID
     */
    public void deleteInvitation(Long id) {
        invitationRepository.deleteById(id);
    }
    
    /**
     * Scheduled task to clean up expired invitations (runs daily at midnight)
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupExpiredInvitations() {
        invitationRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        System.out.println("Expired invitations cleanup completed at " + LocalDateTime.now());
    }
} 