package edu.tcu.cs.backend.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class GameScheduleDto {
    
    private Long id;
    
    @NotBlank(message = "Schedule name is required.")
    private String name;
    
    private String description;
    
    @NotBlank(message = "Season is required.")
    private String season;
    
    @NotBlank(message = "Sport is required.")
    private String sport;
    
    private List<Long> gameIds = new ArrayList<>();
    
    public GameScheduleDto() {}
    
    public GameScheduleDto(Long id, String name, String description, String season, String sport, List<Long> gameIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.season = season;
        this.sport = sport;
        this.gameIds = gameIds;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getSeason() {
        return season;
    }
    
    public void setSeason(String season) {
        this.season = season;
    }
    
    public String getSport() {
        return sport;
    }
    
    public void setSport(String sport) {
        this.sport = sport;
    }
    
    public List<Long> getGameIds() {
        return gameIds;
    }
    
    public void setGameIds(List<Long> gameIds) {
        this.gameIds = gameIds;
    }
} 