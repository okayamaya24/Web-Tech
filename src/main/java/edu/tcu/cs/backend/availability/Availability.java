package edu.tcu.cs.backend.availability;

import jakarta.persistence.*;

@Entity
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long crewMemberId;
    private Long gameId; 

    private boolean available; 
    private String comment;    

    public Availability() {}

    public Availability(Long crewMemberId, Long gameId, boolean available, String comment) {
        this.crewMemberId = crewMemberId;
        this.gameId = gameId;
        this.available = available;
        this.comment = comment;
    }

    // Getters and Setters here...

    public Long getCrewMemberId() {
        return crewMemberId;
    }

    public void setCrewMemberId(Long crewMemberId) {
        this.crewMemberId = crewMemberId;
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
