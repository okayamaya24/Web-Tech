package edu.tcu.cs.backend.game;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sport;

    private LocalDate gameDate;

    private LocalTime gameTime;

    private String venue;

    private String opponent;

    @ElementCollection
    private List<String> requiredCrewPositions;

    // Getters
    public Long getId() {
        return id;
    }

    public String getSport() {
        return sport;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public LocalTime getGameTime() {
        return gameTime;
    }

    public String getVenue() {
        return venue;
    }

    public String getOpponent() {
        return opponent;
    }

    public List<String> getRequiredCrewPositions() {
        return requiredCrewPositions;
    }

    // Setters
    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public void setGameTime(LocalTime gameTime) {
        this.gameTime = gameTime;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public void setRequiredCrewPositions(List<String> requiredCrewPositions) {
        this.requiredCrewPositions = requiredCrewPositions;
    }
}
