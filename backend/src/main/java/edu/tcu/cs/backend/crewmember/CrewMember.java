package edu.tcu.cs.backend.crewmember;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CrewMember {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String role;
    private String qualifiedPosition;

    public CrewMember() {
    }

    public CrewMember(String firstName, String lastName, String email, String phone, String password, String role, String qualifiedPosition) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.qualifiedPosition = qualifiedPosition;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
