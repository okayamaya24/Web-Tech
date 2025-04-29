package edu.tcu.cs.backend.availability;

import edu.tcu.cs.backend.availability.AvailabilityRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/availability")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Availability", description = "Endpoints for managing crew member availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;
    private final AvailabilityRepository availabilityRepository;

    public AvailabilityController(AvailabilityService availabilityService, AvailabilityRepository availabilityRepository) {
        this.availabilityService = availabilityService;
        this.availabilityRepository = availabilityRepository;
    }

    @PostMapping("/{gameId}")
    @Operation(summary = "Submit availability for a game", 
               description = "Allows a crew member to submit their availability for a specific game. The crew member can mark themselves as available or unavailable and provide a comment.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Availability submitted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "404", description = "Game or crew member not found")
    })
    public ResponseEntity<?> submitAvailability(
            @Parameter(description = "ID of the game", required = true) @PathVariable Long gameId,
            @Parameter(description = "Availability details", required = true) 
                @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Contains boolean 'available' flag and optional 'comment'",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AvailabilityRequestDTO.class))
                )
            @RequestBody AvailabilityRequestDTO request,
            @Parameter(description = "Email of the crew member", required = true) 
            @RequestParam String crewMemberEmail) {
        
        try {
            Availability submitted = availabilityService.submitAvailabilityByEmail(gameId, crewMemberEmail, request);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "gameId", submitted.getGameId(),
                "crewMemberId", submitted.getCrewMemberId(),
                "available", submitted.isAvailable(),
                "comment", submitted.getComment() != null ? submitted.getComment() : ""
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/games/upcoming")
    @Operation(summary = "Get upcoming games for availability submission", 
               description = "Retrieves a list of upcoming games for which the crew member can submit availability")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of upcoming games retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid crew member email")
    })
    public ResponseEntity<?> getUpcomingGamesForAvailability(
            @Parameter(description = "Email of the crew member", required = true) 
            @RequestParam String crewMemberEmail) {
        try {
            return ResponseEntity.ok(availabilityService.getUpcomingGamesForAvailability(crewMemberEmail));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping
    @Operation(summary = "Get all availabilities", description = "Retrieves all availability records. Admin only.")
    @ApiResponse(responseCode = "200", description = "List of all availabilities")
    public List<Availability> getAllAvailability(
            @Parameter(description = "Admin email for authorization", required = true)
            @RequestParam String adminEmail) {
        // Verify admin
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            throw new RuntimeException("Only admin can view all availability records");
        }
        return availabilityRepository.findAll();
    }
    
    @GetMapping("/game/{gameId}")
    @Operation(summary = "Get availabilities by game", description = "Retrieves all availability records for a specific game. Admin only.")
    @ApiResponse(responseCode = "200", description = "List of availabilities for the specified game")
    public ResponseEntity<?> getAvailabilityByGame(
            @Parameter(description = "ID of the game", required = true) @PathVariable Long gameId,
            @Parameter(description = "Admin email for authorization", required = true)
            @RequestParam String adminEmail) {
        // Verify admin
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            return ResponseEntity.status(403).body(Map.of(
                "success", false,
                "message", "Only admin can view game availability records"
            ));
        }
        return ResponseEntity.ok(availabilityService.getAvailabilityByGameId(gameId));
    }
    
    @GetMapping("/crew-member")
    @Operation(summary = "Get availabilities by crew member", description = "Retrieves all availability records for the specified crew member")
    @ApiResponse(responseCode = "200", description = "List of availabilities for the specified crew member")
    public ResponseEntity<?> getAvailabilityByCrewMemberEmail(
            @Parameter(description = "Email of the crew member", required = true) 
            @RequestParam String crewMemberEmail) {
        try {
            return ResponseEntity.ok(availabilityService.getAvailabilityByCrewMemberEmail(crewMemberEmail));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/game/{gameId}/crew-member")
    @Operation(summary = "Get specific availability", description = "Retrieves a specific availability record for a game and crew member")
    @ApiResponse(responseCode = "200", description = "The specific availability record")
    @ApiResponse(responseCode = "404", description = "Availability record not found")
    public ResponseEntity<?> getSpecificAvailabilityByEmail(
            @Parameter(description = "ID of the game", required = true) @PathVariable Long gameId, 
            @Parameter(description = "Email of the crew member", required = true) 
            @RequestParam String crewMemberEmail) {
        try {
            return ResponseEntity.ok(availabilityService.getSpecificAvailabilityByEmail(gameId, crewMemberEmail));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
    
    @DeleteMapping("/game/{gameId}/crew-member")
    @Operation(summary = "Delete a specific availability", description = "Deletes a specific availability record for a game and crew member")
    @ApiResponse(responseCode = "200", description = "Availability deleted successfully")
    @ApiResponse(responseCode = "404", description = "Availability record not found")
    public ResponseEntity<?> deleteAvailabilityByEmail(
            @Parameter(description = "ID of the game", required = true) @PathVariable Long gameId,
            @Parameter(description = "Email of the crew member", required = true) 
            @RequestParam String crewMemberEmail) {
        try {
            availabilityService.deleteAvailabilityByEmail(gameId, crewMemberEmail);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Availability deleted successfully"
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
}
