package edu.tcu.cs.backend.crewassignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crew-assignments")
@CrossOrigin(origins = "http://localhost:5173")
public class CrewAssignmentController {

    private final CrewAssignmentService crewAssignmentService;

    @Autowired
    public CrewAssignmentController(CrewAssignmentService crewAssignmentService) {
        this.crewAssignmentService = crewAssignmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CrewAssignment createCrewAssignment(@RequestBody CrewAssignment crewAssignment) {
        return crewAssignmentService.save(crewAssignment);
    }

    @GetMapping("/game/{gameId}")
    public List<CrewAssignment> getCrewAssignmentsByGame(@PathVariable Long gameId) {
        return crewAssignmentService.getCrewAssignmentsByGameId(gameId);
    }
}
