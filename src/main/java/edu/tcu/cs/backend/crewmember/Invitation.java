package edu.tcu.cs.backend.crewmember;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "invitation")
public class Invitation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private String qualifiedPosition;
    
    @Column(nullable = false, unique = true)
    private String token;
    
    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;
    
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    
    @Column(nullable = false)
    private boolean accepted;
    
    // Default constructor for JPA
    protected Invitation() {
    }
    
    // Constructor with required fields
    public Invitation(String email, String firstName, String lastName, String qualifiedPosition) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.qualifiedPosition = qualifiedPosition;
        this.token = UUID.randomUUID().toString();
        this.sentAt = LocalDateTime.now();
        this.expiresAt = this.sentAt.plusDays(7); // Set expiration to 7 days from now
        this.accepted = false;
    }
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getQualifiedPosition() {
        return qualifiedPosition;
    }
    
    public void setQualifiedPosition(String qualifiedPosition) {
        this.qualifiedPosition = qualifiedPosition;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public LocalDateTime getSentAt() {
        return sentAt;
    }
    
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
    
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
    
    public boolean isAccepted() {
        return accepted;
    }
    
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
    
    // Check if the invitation is expired
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
    
    // Mark invitation as accepted
    public void markAsAccepted() {
        this.accepted = true;
    }
    
    // Generate a new token
    public void refreshToken() {
        this.token = UUID.randomUUID().toString();
        this.sentAt = LocalDateTime.now();
        this.expiresAt = this.sentAt.plusDays(7);
    }
} 