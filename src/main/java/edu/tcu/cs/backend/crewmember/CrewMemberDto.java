package edu.tcu.cs.backend.crewmember;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data transfer object for crew member information")
public class CrewMemberDto {

    @Schema(description = "First name of the crew member", example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;
    
    @Schema(description = "Last name of the crew member", example = "Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;
    
    @Schema(description = "Email address of the crew member (used for login)", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    
    @Schema(description = "Phone number of the crew member", example = "555-123-4567")
    private String phoneNumber;
    
    @Schema(description = "Password for the account (not returned in responses)", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    
    @Schema(description = "Role of the crew member (crew or admin)", example = "crew", defaultValue = "crew")
    private String role;
    
    @Schema(description = "Position the crew member is qualified for", example = "Camera Operator", requiredMode = Schema.RequiredMode.REQUIRED)
    private String qualifiedPosition;

    // getters and setters

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getQualifiedPosition() {
        return qualifiedPosition;
    }

    public void setQualifiedPosition(String qualifiedPosition) {
        this.qualifiedPosition = qualifiedPosition;
    }
}
