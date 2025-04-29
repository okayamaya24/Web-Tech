package edu.tcu.cs.backend.crewassignment;

import edu.tcu.cs.backend.crewassignment.dto.CrewAssignmentDto;
import edu.tcu.cs.backend.crewassignment.dto.MultipleAssignmentsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crew-assignments")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Crew Assignments", description = "API endpoints for managing crew assignments to games")
public class CrewAssignmentController {

    private final CrewAssignmentService crewAssignmentService;

    @Autowired
    public CrewAssignmentController(CrewAssignmentService crewAssignmentService) {
        this.crewAssignmentService = crewAssignmentService;
    }

    @PostMapping
    @Operation(summary = "Assign a crew member to a game position", 
               description = "Admin can assign a specific crew member to a position for a game. Admin access required.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Crew assignment created successfully",
                     content = @Content(schema = @Schema(implementation = CrewAssignmentDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or validation error"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin only")
    })
    public ResponseEntity<?> createCrewAssignment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Crew assignment details", 
                required = true, content = @Content(schema = @Schema(implementation = CrewAssignmentDto.class)))
            @RequestBody CrewAssignmentDto crewAssignmentDto,
            @Parameter(description = "Admin email for authorization", required = true)
            @RequestParam String adminEmail) {
        // Verify that the request is coming from an admin user
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can create crew assignments");
        }
        
        try {
            CrewAssignmentDto createdAssignment = crewAssignmentService.createCrewAssignment(crewAssignmentDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAssignment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/bulk")
    @Operation(summary = "Assign multiple crew members to game positions", 
               description = "Admin can assign multiple crew members to positions for a game in one operation. This is the recommended endpoint for the 'Admin Schedules Crew' use case. Admin access required.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Crew assignments created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or validation error"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin only")
    })
    public ResponseEntity<?> createMultipleAssignments(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Multiple crew assignments details", 
                required = true, content = @Content(schema = @Schema(implementation = MultipleAssignmentsDto.class)))
            @RequestBody MultipleAssignmentsDto multipleAssignmentsDto,
            @Parameter(description = "Admin email for authorization", required = true)
            @RequestParam String adminEmail) {
        // Verify that the request is coming from an admin user
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can create crew assignments");
        }
        
        try {
            // Validate the game exists
            if (multipleAssignmentsDto.getGameId() == null) {
                return ResponseEntity.badRequest().body("Game ID is required");
            }
            
            // Validate that assignments are provided
            if (multipleAssignmentsDto.getAssignments() == null || multipleAssignmentsDto.getAssignments().isEmpty()) {
                return ResponseEntity.badRequest().body("At least one assignment is required");
            }
            
            List<CrewAssignmentDto> createdAssignments = crewAssignmentService.createMultipleCrewAssignments(multipleAssignmentsDto);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(
                java.util.Map.of(
                    "success", true,
                    "message", String.format("Created %d crew assignments for game %d", 
                               createdAssignments.size(), multipleAssignmentsDto.getGameId()),
                    "assignments", createdAssignments
                )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of(
                    "success", false,
                    "message", e.getMessage()
                )
            );
        }
    }

    @GetMapping("/game/{gameId}")
    @Operation(summary = "Get all crew assignments for a game", 
               description = "Retrieves all crew assignments for a specific game")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Crew assignments retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid game ID or game not found")
    })
    public ResponseEntity<?> getCrewAssignmentsByGame(
            @Parameter(description = "ID of the game", required = true)
            @PathVariable Long gameId) {
        try {
            List<CrewAssignmentDto> assignments = crewAssignmentService.getCrewAssignmentDtosByGameId(gameId);
            return ResponseEntity.ok(assignments);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove a crew assignment", 
               description = "Admin can remove a specific crew assignment by ID. Admin access required.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Crew assignment deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid ID or assignment not found"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin only")
    })
    public ResponseEntity<?> deleteCrewAssignment(
            @Parameter(description = "ID of the crew assignment to delete", required = true)
            @PathVariable Long id,
            @Parameter(description = "Admin email for authorization", required = true)
            @RequestParam String adminEmail) {
        // Verify that the request is coming from an admin user
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can delete crew assignments");
        }
        
        try {
            crewAssignmentService.deleteCrewAssignment(id);
            return ResponseEntity.ok("Crew assignment deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/game/{gameId}")
    @Operation(summary = "Remove all crew assignments for a game", 
               description = "Admin can remove all crew assignments for a specific game. Admin access required.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All crew assignments for game deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid game ID or game not found"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin only")
    })
    public ResponseEntity<?> deleteAllAssignmentsForGame(
            @Parameter(description = "ID of the game", required = true)
            @PathVariable Long gameId,
            @Parameter(description = "Admin email for authorization", required = true)
            @RequestParam String adminEmail) {
        // Verify that the request is coming from an admin user
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can delete crew assignments");
        }
        
        try {
            crewAssignmentService.deleteAllAssignmentsForGame(gameId);
            return ResponseEntity.ok("All crew assignments for game deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/recommendations/{gameId}")
    @Operation(summary = "Get crew recommendations for a game", 
               description = "Admin can view recommended crew members for a game based on qualifications and availability. Admin access required.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Crew recommendations retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid game ID or game not found"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin only")
    })
    public ResponseEntity<?> getRecommendationsForGame(
            @Parameter(description = "ID of the game", required = true)
            @PathVariable Long gameId,
            @Parameter(description = "Admin email for authorization", required = true)
            @RequestParam String adminEmail) {
        // Verify that the request is coming from an admin user
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can access crew recommendations");
        }
        
        try {
            List<CrewAssignmentService.RecommendationDTO> recommendations = 
                    crewAssignmentService.getRecommendedCrewForGame(gameId);
            return ResponseEntity.ok(recommendations);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/game/{gameId}/positions")
    @Operation(summary = "Get all required positions for a game", 
               description = "Retrieves all positions that need to be filled for a specific game, showing both filled and unfilled positions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Positions retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid game ID or game not found")
    })
    public ResponseEntity<?> getRequiredPositionsForGame(
            @Parameter(description = "ID of the game", required = true)
            @PathVariable Long gameId) {
        try {
            return ResponseEntity.ok(crewAssignmentService.getRequiredPositionsForGame(gameId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/upcoming")
    @Operation(summary = "Get upcoming games with crew assignment status", 
               description = "Admin can view upcoming games with information about their crew assignment status. Admin access required.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Upcoming games with assignment status retrieved successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin only")
    })
    public ResponseEntity<?> getUpcomingGamesWithAssignmentStatus(
            @Parameter(description = "Admin email for authorization", required = true)
            @RequestParam String adminEmail) {
        // Verify that the request is coming from an admin user
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can access this information");
        }
        
        try {
            return ResponseEntity.ok(crewAssignmentService.getUpcomingGamesWithAssignmentStatus());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
