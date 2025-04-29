package edu.tcu.cs.backend.crewassignment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CrewAssignmentRepository extends JpaRepository<CrewAssignment, Long> {
    List<CrewAssignment> findByGameId(Long gameId);
}
