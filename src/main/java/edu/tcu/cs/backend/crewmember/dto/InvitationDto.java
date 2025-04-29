package edu.tcu.cs.backend.crewmember.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class InvitationDto {
    
    @Email(message = "Email must be valid.")
    @NotBlank(message = "Email is required.")
    @JsonProperty("email")
    private String email;
    
    @NotBlank(message = "First name is required.")
    @JsonProperty("firstName")
    private String firstName;
    
    @NotBlank(message = "Last name is required.")
    @JsonProperty("lastName")
    private String lastName;
    
    @NotBlank(message = "Qualified position is required.")
    @JsonProperty("qualifiedPosition")
    private String qualifiedPosition;
    
    // Default constructor
    public InvitationDto() {
    }
    
    // Constructor with all fields
    public InvitationDto(String email, String firstName, String lastName, String qualifiedPosition) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.qualifiedPosition = qualifiedPosition;
    }
    
    // Getters and Setters
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
} 