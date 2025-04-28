package edu.tcu.cs.backend.crewassignment;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CrewAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gameId;           // Link to the game
    private String position;       // Position assigned (ex: Camera, Director)
    private String name;           // Crew member name
    private LocalDateTime reportTime; // When they need to report
    private String reportLocation; // Where they need to report

    public CrewAssignment() {}

    public CrewAssignment(Long gameId, String position, String name, LocalDateTime reportTime, String reportLocation) {
        this.gameId = gameId;
        this.position = position;
        this.name = name;
        this.reportTime = reportTime;
        this.reportLocation = reportLocation;
    }

    // Getters and setters
    public Long getId() { return id; }
    public Long getGameId() { return gameId; }
    public String getPosition() { return position; }
    public String getName() { return name; }
    public LocalDateTime getReportTime() { return reportTime; }
    public String getReportLocation() { return reportLocation; }

    public void setId(Long id) { this.id = id; }
    public void setGameId(Long gameId) { this.gameId = gameId; }
    public void setPosition(String position) { this.position = position; }
    public void setName(String name) { this.name = name; }
    public void setReportTime(LocalDateTime reportTime) { this.reportTime = reportTime; }
    public void setReportLocation(String reportLocation) { this.reportLocation = reportLocation; }
}
