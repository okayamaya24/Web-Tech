package edu.tcu.cs.backend.game;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String time;
    private String venue;
    private String opponent;
    private String sport;

    @ElementCollection
    @CollectionTable(name = "game_crew_positions", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "position")
    private List<String> crewPositions = new ArrayList<>();

    public Game() {}

    public Game(String date, String time, String venue, String opponent, String sport, List<String> crewPositions) {
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.opponent = opponent;
        this.sport = sport;
        this.crewPositions = crewPositions;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public List<String> getCrewPositions() {
        return crewPositions;
    }

    public void setCrewPositions(List<String> crewPositions) {
        this.crewPositions = crewPositions;
    }
}
