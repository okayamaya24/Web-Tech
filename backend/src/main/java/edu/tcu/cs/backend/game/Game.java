package edu.tcu.cs.backend.game;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate gameDate;
    private LocalTime gameTime;
    private String opponent;
    private String venue;
    private String requiredCrewPositions; // e.g. "Referee, Scorekeeper"

    public Game() {
    }

    // Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getGameDate() { return gameDate; }
    public void setGameDate(LocalDate gameDate) { this.gameDate = gameDate; }

    public LocalTime getGameTime() { return gameTime; }
    public void setGameTime(LocalTime gameTime) { this.gameTime = gameTime; }

    public String getOpponent() { return opponent; }
    public void setOpponent(String opponent) { this.opponent = opponent; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getRequiredCrewPositions() { return requiredCrewPositions; }
    public void setRequiredCrewPositions(String requiredCrewPositions) { this.requiredCrewPositions = requiredCrewPositions; }
}
