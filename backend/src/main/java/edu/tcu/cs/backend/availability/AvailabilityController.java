package edu.tcu.cs.backend.availability;

import edu.tcu.cs.backend.availability.AvailabilityRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availability")
@CrossOrigin(origins = "http://localhost:5173")
public class AvailabilityController {

    private final AvailabilityService availabilityService;
    private final AvailabilityRepository availabilityRepository;

    public AvailabilityController(AvailabilityService availabilityService, AvailabilityRepository availabilityRepository) {
        this.availabilityService = availabilityService;
        this.availabilityRepository = availabilityRepository;
    }

    @PostMapping("/{gameId}")
    public ResponseEntity<String> submitAvailability(@PathVariable Long gameId,
                                                     @RequestBody AvailabilityRequestDTO request,
                                                     @RequestParam String userEmail) {
        availabilityService.submitAvailability(gameId, userEmail, request);
        return ResponseEntity.ok("Availability submitted successfully!");
    }

    @GetMapping
    public List<Availability> getAllAvailability() {
        return availabilityRepository.findAll();
    }
}
