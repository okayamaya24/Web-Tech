package edu.tcu.cs.backend.crewassignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrewAssignmentService {

    private final CrewAssignmentRepository crewAssignmentRepository;

    @Autowired
    public CrewAssignmentService(CrewAssignmentRepository crewAssignmentRepository) {
        this.crewAssignmentRepository = crewAssignmentRepository;
    }

    public CrewAssignment save(CrewAssignment crewAssignment) {
        return crewAssignmentRepository.save(crewAssignment);
    }
    public List<CrewAssignment> getCrewAssignmentsByGameId(Long gameId) {
        return crewAssignmentRepository.findByGameId(gameId);
    }
}
