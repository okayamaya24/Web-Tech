package edu.tcu.cs.backend.availability;

import jakarta.persistence.*;

@Entity
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String crewMemberEmail; 
    private Long gameId; 

    private boolean available; 
    private String comment;    

    public Availability() {}

    public Availability(String crewMemberEmail, Long gameId, boolean available, String comment) {
        this.crewMemberEmail = crewMemberEmail;
        this.gameId = gameId;
        this.available = available;
        this.comment = comment;
    }

    // Getters and Setters here...

    public String getCrewMemberEmail() {
        return crewMemberEmail;
    }

    public void setCrewMemberEmail(String crewMemberEmail) {
        this.crewMemberEmail = crewMemberEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
