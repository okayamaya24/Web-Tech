package edu.tcu.cs.backend.crewmember;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/crew-members")
public class CrewMemberController {

    public final CrewMemberService service;

    public CrewMemberController(CrewMemberService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrewMember> getCrewMemberById(@PathVariable Long id) {
        CrewMember found = service.getCrewMemberById(id);
        return ResponseEntity.ok(found);
    }
}
