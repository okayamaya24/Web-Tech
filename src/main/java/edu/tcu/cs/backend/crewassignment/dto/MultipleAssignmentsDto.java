package edu.tcu.cs.backend.crewassignment.dto;

import java.util.List;

public class MultipleAssignmentsDto {
    
    private Long gameId;
    private List<CrewAssignmentDto> assignments;
    
    public MultipleAssignmentsDto() {}
    
    public MultipleAssignmentsDto(Long gameId, List<CrewAssignmentDto> assignments) {
        this.gameId = gameId;
        this.assignments = assignments;
    }
    
    public Long getGameId() {
        return gameId;
    }
    
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
    
    public List<CrewAssignmentDto> getAssignments() {
        return assignments;
    }
    
    public void setAssignments(List<CrewAssignmentDto> assignments) {
        this.assignments = assignments;
    }
} 