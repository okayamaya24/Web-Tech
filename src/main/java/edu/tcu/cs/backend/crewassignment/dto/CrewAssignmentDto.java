package edu.tcu.cs.backend.crewassignment.dto;

import java.time.LocalDateTime;

public class CrewAssignmentDto {
    
    private Long id;
    private Long gameId;
    private String position;
    private String name;  // This is the crew member's email
    private LocalDateTime reportTime;
    private String reportLocation;
    
    public CrewAssignmentDto() {}
    
    public CrewAssignmentDto(Long id, Long gameId, String position, String name, 
                           LocalDateTime reportTime, String reportLocation) {
        this.id = id;
        this.gameId = gameId;
        this.position = position;
        this.name = name;
        this.reportTime = reportTime;
        this.reportLocation = reportLocation;
    }
    
    // Getters and Setters
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
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public LocalDateTime getReportTime() {
        return reportTime;
    }
    
    public void setReportTime(LocalDateTime reportTime) {
        this.reportTime = reportTime;
    }
    
    public String getReportLocation() {
        return reportLocation;
    }
    
    public void setReportLocation(String reportLocation) {
        this.reportLocation = reportLocation;
    }
} 