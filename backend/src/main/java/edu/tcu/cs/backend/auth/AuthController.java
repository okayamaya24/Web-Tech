package edu.tcu.cs.backend.auth;

import edu.tcu.cs.backend.crewmember.CrewMember;
import edu.tcu.cs.backend.crewmember.CrewMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final CrewMemberRepository crewMemberRepository;

    @Autowired
    public AuthController(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        CrewMember user = crewMemberRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user != null && user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.ok(new LoginResponse(user.getEmail(), user.getRole()));
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
