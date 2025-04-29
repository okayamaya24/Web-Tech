package edu.tcu.cs.backend.schedule;

import edu.tcu.cs.backend.schedule.dto.GameScheduleDto;
import edu.tcu.cs.backend.schedule.dto.GameToScheduleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Game Schedule", description = "Game Schedule Management APIs")
public class GameScheduleController {
    
    private final GameScheduleService gameScheduleService;
    
    public GameScheduleController(GameScheduleService gameScheduleService) {
        this.gameScheduleService = gameScheduleService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new game schedule", 
               description = "Creates a new game schedule with the provided details such as name, season, and year. Admin access required.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Schedule created successfully",
                content = @Content(schema = @Schema(implementation = GameScheduleDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin only")
    })
    public ResponseEntity<?> createSchedule(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Game schedule data", 
                required = true, content = @Content(schema = @Schema(implementation = GameScheduleDto.class)))
            @RequestBody GameScheduleDto gameScheduleDto,
            @Parameter(description = "Admin email for authentication", required = true)
            @RequestParam String adminEmail) {
        // Verify that the request is coming from an admin user
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can create schedules");
        }
        
        try {
            GameScheduleDto createdSchedule = gameScheduleService.createSchedule(gameScheduleDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping
    @Operation(summary = "Get all game schedules", description = "Retrieves a list of all game schedules")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all schedules",
            content = @Content(schema = @Schema(implementation = GameScheduleDto.class)))
    public List<GameScheduleDto> getAllSchedules() {
        return gameScheduleService.getAllSchedules();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get game schedule by ID", description = "Retrieves a game schedule by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the schedule",
                content = @Content(schema = @Schema(implementation = GameScheduleDto.class))),
        @ApiResponse(responseCode = "400", description = "Schedule not found")
    })
    public ResponseEntity<?> getScheduleById(
            @Parameter(description = "ID of the schedule to retrieve", required = true)
            @PathVariable Long id) {
        try {
            GameScheduleDto schedule = gameScheduleService.getScheduleById(id);
            return ResponseEntity.ok(schedule);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/{scheduleId}/games")
    @Operation(summary = "Add game to schedule", 
               description = "Adds a game to an existing schedule. This endpoint allows the admin to associate an existing game with a game schedule. Admin access required.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Game added to schedule successfully",
                content = @Content(schema = @Schema(implementation = GameScheduleDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or schedule/game not found"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin only")
    })
    public ResponseEntity<?> addGameToSchedule(
            @Parameter(description = "ID of the schedule", required = true)
            @PathVariable Long scheduleId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Game ID to add to schedule", 
                required = true, content = @Content(schema = @Schema(implementation = GameToScheduleDto.class)))
            @RequestBody GameToScheduleDto gameToScheduleDto,
            @Parameter(description = "Admin email for authentication", required = true)
            @RequestParam String adminEmail) {
        // Verify that the request is coming from an admin user
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can add games to schedules");
        }
        
        try {
            GameScheduleDto updatedSchedule = gameScheduleService.addGameToSchedule(scheduleId, gameToScheduleDto.getGameId());
            return ResponseEntity.ok(updatedSchedule);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{scheduleId}/games/{gameId}")
    @Operation(summary = "Remove game from schedule", description = "Removes a game from an existing schedule. Admin access required.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Game removed from schedule successfully",
                content = @Content(schema = @Schema(implementation = GameScheduleDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or schedule/game not found"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin only")
    })
    public ResponseEntity<?> removeGameFromSchedule(
            @Parameter(description = "ID of the schedule", required = true)
            @PathVariable Long scheduleId,
            @Parameter(description = "ID of the game to remove", required = true)
            @PathVariable Long gameId,
            @Parameter(description = "Admin email for authentication", required = true)
            @RequestParam String adminEmail) {
        // Verify that the request is coming from an admin user
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can remove games from schedules");
        }
        
        try {
            GameScheduleDto updatedSchedule = gameScheduleService.removeGameFromSchedule(scheduleId, gameId);
            return ResponseEntity.ok(updatedSchedule);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "Delete game schedule", description = "Deletes a game schedule by its ID. Admin access required.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Schedule deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Schedule not found"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin only")
    })
    public ResponseEntity<?> deleteSchedule(
            @Parameter(description = "ID of the schedule to delete", required = true)
            @PathVariable Long scheduleId,
            @Parameter(description = "Admin email for authentication", required = true)
            @RequestParam String adminEmail) {
        // Verify that the request is coming from an admin user
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can delete schedules");
        }
        
        try {
            gameScheduleService.deleteSchedule(scheduleId);
            return ResponseEntity.ok("Schedule deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 