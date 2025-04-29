package edu.tcu.cs.backend.auth;

import edu.tcu.cs.backend.crewmember.CrewMember;
import edu.tcu.cs.backend.crewmember.CrewMemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final CrewMemberRepository crewMemberRepository;

    public AuthController(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        CrewMember crewMember = crewMemberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email."));

        if (crewMember.getPassword() == null || !crewMember.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid password.");
        }

        // Decide the role based on email
        String role;
        if ("admin@example.com".equalsIgnoreCase(crewMember.getEmail())) {
            role = "admin";
        } else {
            role = "crewMember";
        }

        return ResponseEntity.ok(new LoginResponse(crewMember.getEmail(), role));
    }
}

