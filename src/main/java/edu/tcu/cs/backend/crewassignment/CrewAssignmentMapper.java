package edu.tcu.cs.backend.crewassignment;

import edu.tcu.cs.backend.crewassignment.dto.CrewAssignmentDto;

public class CrewAssignmentMapper {
    
    public static CrewAssignmentDto toDto(CrewAssignment crewAssignment) {
        return new CrewAssignmentDto(
            crewAssignment.getId(),
            crewAssignment.getGameId(),
            crewAssignment.getPosition(),
            crewAssignment.getName(),
            crewAssignment.getReportTime(),
            crewAssignment.getReportLocation()
        );
    }
    
    public static CrewAssignment toEntity(CrewAssignmentDto dto) {
        CrewAssignment crewAssignment = new CrewAssignment();
        crewAssignment.setId(dto.getId());
        crewAssignment.setGameId(dto.getGameId());
        crewAssignment.setPosition(dto.getPosition());
        crewAssignment.setName(dto.getName());
        crewAssignment.setReportTime(dto.getReportTime());
        crewAssignment.setReportLocation(dto.getReportLocation());
        return crewAssignment;
    }
} 