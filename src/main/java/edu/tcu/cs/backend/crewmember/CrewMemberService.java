package edu.tcu.cs.backend.crewmember;

import edu.tcu.cs.backend.availability.AvailabilityRepository;
import edu.tcu.cs.backend.crewmember.dto.CrewMemberRequestDTO;
import edu.tcu.cs.backend.crewmember.dto.InvitationDto;
import edu.tcu.cs.backend.system.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CrewMemberService {

    private final CrewMemberRepository crewMemberRepository;
    @Autowired
    private AvailabilityRepository availabilityRepository;
    @Autowired
    private EmailService emailService;

    public CrewMemberService(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    public CrewMemberDto registerCrewMember(CrewMemberDto crewMemberDto) {
        CrewMember crewMember = new CrewMember();
        crewMember.setFirstName(crewMemberDto.getFirstName());
        crewMember.setLastName(crewMemberDto.getLastName());
        crewMember.setEmail(crewMemberDto.getEmail());
        crewMember.setPhoneNumber(crewMemberDto.getPhoneNumber());
        crewMember.setPassword(crewMemberDto.getPassword());
        crewMember.setRole(crewMemberDto.getRole());
        crewMember.setQualifiedPosition(crewMemberDto.getQualifiedPosition());
        crewMemberRepository.save(crewMember);
        return CrewMemberMapper.toDto(crewMember);
    }

    public CrewMemberDto findByEmail(String email) {
        CrewMember crewMember = crewMemberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Crew Member not found with email: " + email));
        return CrewMemberMapper.toDto(crewMember);
    }
    
    public List<CrewMember> getAllCrewMembers() {
        return crewMemberRepository.findAll();
    }
    
    public CrewMemberDto inviteCrewMember(InvitationDto invitationDto) {
        // Validate inputs
        if (invitationDto == null || invitationDto.getEmail() == null || invitationDto.getEmail().trim().isEmpty() ||
            invitationDto.getFirstName() == null || invitationDto.getFirstName().trim().isEmpty() ||
            invitationDto.getLastName() == null || invitationDto.getLastName().trim().isEmpty() ||
            invitationDto.getQualifiedPosition() == null || invitationDto.getQualifiedPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("All crew member fields (email, firstName, lastName, qualifiedPosition) are required");
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
        
        // Create a new crew member with the provided details
        CrewMember crewMember = new CrewMember();
        crewMember.setFirstName(invitationDto.getFirstName());
        crewMember.setLastName(invitationDto.getLastName());
        crewMember.setEmail(invitationDto.getEmail());
        crewMember.setQualifiedPosition(invitationDto.getQualifiedPosition());
        
        // Set default role as "crew"
        crewMember.setRole("crew");
        
        // Generate a temporary password
        String temporaryPassword = generateTemporaryPassword();
        crewMember.setPassword(temporaryPassword);
        
        // Set an empty phone number that can be updated by the user later
        crewMember.setPhoneNumber("");
        
        // Save the crew member
        CrewMember savedCrewMember = crewMemberRepository.save(crewMember);
        
        // Send invitation email
        try {
            emailService.sendInvitationEmail(
                invitationDto.getEmail(),
                invitationDto.getFirstName(),
                invitationDto.getLastName(),
                temporaryPassword
            );
            System.out.println("Invitation email sent to " + invitationDto.getEmail());
        } catch (MessagingException e) {
            // Log the error but continue with the process
            System.err.println("Failed to send invitation email: " + e.getMessage());
            e.printStackTrace();
        }
        
        return CrewMemberMapper.toDto(savedCrewMember);
    }
    
    public void deleteCrewMember(String email) {
        CrewMember crewMember = crewMemberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Crew Member not found with email: " + email));
        
        // Don't allow deletion of admin
        if ("admin@example.com".equalsIgnoreCase(email)) {
            throw new RuntimeException("Cannot delete admin account");
        }
        
        // Delete all availability records associated with this crew member
        availabilityRepository.deleteByCrewMemberId(crewMember.getId());
        
        // Delete the crew member
        crewMemberRepository.delete(crewMember);
        
        System.out.println("Crew member deleted: " + email);
    }
    
    private String generateTemporaryPassword() {
        // Simple method to generate a random 8-character password
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
}
