package edu.tcu.cs.backend.crewmember;

import edu.tcu.cs.backend.crewmember.dto.CrewMemberRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/crew-members")
@CrossOrigin(origins = "http://localhost:5173")
public class CrewMemberController {

    private final CrewMemberService crewMemberService;
    private final CrewMemberRepository crewMemberRepository;

    @Autowired
    public CrewMemberController(CrewMemberService crewMemberService, CrewMemberRepository crewMemberRepository) {
        this.crewMemberService = crewMemberService;
        this.crewMemberRepository = crewMemberRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCrewMember(@RequestBody CrewMemberDto crewMemberDto) {
        if ("admin@example.com".equalsIgnoreCase(crewMemberDto.getEmail())) {
            return ResponseEntity.badRequest().body("Cannot register admin through registration page.");
        }
        CrewMemberDto savedCrewMember = crewMemberService.registerCrewMember(crewMemberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCrewMember);
    }

    @GetMapping("/{email}")
    public ResponseEntity<CrewMemberDto> getCrewMemberByEmail(@PathVariable String email) {
        CrewMemberDto crewMemberDto = crewMemberService.findByEmail(email);
        return ResponseEntity.ok(crewMemberDto);
    }

    @GetMapping
    public List<CrewMember> getAllCrewMembers() {
        return crewMemberRepository.findAll();
    }
}
