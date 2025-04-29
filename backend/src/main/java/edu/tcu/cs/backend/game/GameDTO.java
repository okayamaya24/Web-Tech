package edu.tcu.cs.backend.game;

import java.util.List;

public class GameDTO {

    private Long id;
    private String date;
    private String time;
    private String venue;
    private String opponent;
    private String sport;
    private List<String> crewPositions; // <--- THIS is the new field you need!

    // Constructors
    public GameDTO() {}

    public GameDTO(Long id, String date, String time, String venue, String opponent, String sport, List<String> crewPositions) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.opponent = opponent;
        this.sport = sport;
        this.crewPositions = crewPositions;
    }

    // Getters and Setters
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
