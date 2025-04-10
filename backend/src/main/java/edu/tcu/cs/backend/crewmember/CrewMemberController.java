package edu.tcu.cs.backend.crewmember;

import edu.tcu.cs.backend.dto.CrewMemberRegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crew-members")
public class CrewMemberController {

    private final CrewMemberService crewMemberService;

    public CrewMemberController(CrewMemberService crewMemberService) {
        this.crewMemberService = crewMemberService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCrewMember(@Valid @RequestBody CrewMemberRegistrationRequest request) {
        crewMemberService.registerCrewMember(request);
        return new ResponseEntity<>("Crew member registered successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrewMember> getCrewMemberById(@PathVariable Long id) {
        CrewMember crewMember = crewMemberService.getCrewMemberById(id);
        return new ResponseEntity<>(crewMember, HttpStatus.OK);
    }
}