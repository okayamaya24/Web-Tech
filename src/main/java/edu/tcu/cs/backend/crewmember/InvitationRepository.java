package edu.tcu.cs.backend.crewmember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    
    // Find an invitation by token
    Optional<Invitation> findByToken(String token);
    
    // Find an invitation by email
    Optional<Invitation> findByEmail(String email);
    
    // Find all non-expired and non-accepted invitations
    List<Invitation> findByExpiresAtAfterAndAcceptedFalse(LocalDateTime now);
    
    // Check if an invitation exists for an email
    boolean existsByEmail(String email);
    
    // Delete all expired invitations
    void deleteByExpiresAtBefore(LocalDateTime now);
    
    // Check if an invitation with the given token exists
    boolean existsByToken(String token);
} 