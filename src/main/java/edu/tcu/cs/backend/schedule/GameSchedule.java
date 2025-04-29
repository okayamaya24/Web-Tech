package edu.tcu.cs.backend.schedule;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GameSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String season;    // e.g., "Spring 2023", "Fall 2023"
    private String sport;     // Basketball, Football, etc.
    
    @ElementCollection
    @CollectionTable(name = "schedule_game_ids", joinColumns = @JoinColumn(name = "schedule_id"))
    @Column(name = "game_id")
    private List<Long> gameIds = new ArrayList<>();
    
    public GameSchedule() {}
    
    public GameSchedule(String name, String description, String season, String sport) {
        this.name = name;
        this.description = description;
        this.season = season;
        this.sport = sport;
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
    
    public void addGameId(Long gameId) {
        if (!gameIds.contains(gameId)) {
            gameIds.add(gameId);
        }
    }
    
    public void removeGameId(Long gameId) {
        gameIds.remove(gameId);
    }
} 