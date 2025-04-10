package edu.tcu.cs.backend.crewmember;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "crew_members", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class CrewMember implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String phoneNumber;
    private String password;
    private String role;
    private String qualifiedPosition;

    public CrewMember() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getQualifiedPosition() { return qualifiedPosition; }
    public void setQualifiedPosition(String qualifiedPosition) { this.qualifiedPosition = qualifiedPosition; }
}


