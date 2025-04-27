package edu.tcu.cs.backend.crewmember;

import edu.tcu.cs.backend.crewmember.dto.CrewMemberRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crew-members")
@CrossOrigin(origins = "http://localhost:5173")
public class CrewMemberController {

    private final CrewMemberService crewMemberService;

    @Autowired
    public CrewMemberController(CrewMemberService crewMemberService) {
        this.crewMemberService = crewMemberService;
    }

    @PostMapping("/register") // <--- ADD /register here
    public CrewMember registerCrewMember(@RequestBody CrewMemberRequestDTO crewMemberRequestDTO) {
        return crewMemberService.createCrewMember(crewMemberRequestDTO);
    }
    @GetMapping("/{email}")
    public CrewMember viewCrewMemberProfile(@PathVariable String email) {
        return crewMemberService.getCrewMemberByEmail(email);
    }

    @PutMapping("/{email}")
    public CrewMember updateCrewMemberProfile(@PathVariable String email, @RequestBody CrewMember updatedCrewMember) {
        return crewMemberService.updateCrewMember(email, updatedCrewMember);
    }
}
