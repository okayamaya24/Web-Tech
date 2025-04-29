package edu.tcu.cs.backend.system;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Health Check", description = "Endpoints for system health monitoring")
public class HealthCheckController {

    @GetMapping
    @Operation(summary = "Check API Status", description = "Verifies that the API is running")
    @ApiResponse(responseCode = "200", description = "API is running")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API is running");
    }
} 