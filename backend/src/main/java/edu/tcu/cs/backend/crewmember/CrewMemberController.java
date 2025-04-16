package edu.tcu.cs.backend.crewmember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.tcu.cs.backend.crewmember.dto.CrewMemberRequestDTO;

@RestController
@RequestMapping("/api/crew-members")
public class CrewMemberController {

    private final CrewMemberService crewMemberService;

    @Autowired
    public CrewMemberController(CrewMemberService crewMemberService) {
        this.crewMemberService = crewMemberService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CrewMemberRequestDTO dto) {
        try {
            CrewMember created = crewMemberService.createProfile(dto);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
        @GetMapping("/{email}")
        public ResponseEntity<?> getProfile(@PathVariable String email) {
            CrewMember crewMember = crewMemberService.findByEmail(email);
            if (crewMember != null) {
                return ResponseEntity.ok(crewMember);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }
