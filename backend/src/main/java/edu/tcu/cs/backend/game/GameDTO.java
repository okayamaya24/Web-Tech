package edu.tcu.cs.backend.game;

import java.util.List;

public class GameDTO {
    private Long id;
    private String sport;
    private String datetime;
    private String venue;
    private String opponent;
    private List<String> crew;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSport() { return sport; }
    public void setSport(String sport) { this.sport = sport; }

    public String getDatetime() { return datetime; }
    public void setDatetime(String datetime) { this.datetime = datetime; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getOpponent() { return opponent; }
    public void setOpponent(String opponent) { this.opponent = opponent; }

    public List<String> getCrew() { return crew; }
    public void setCrew(List<String> crew) { this.crew = crew; }
}
