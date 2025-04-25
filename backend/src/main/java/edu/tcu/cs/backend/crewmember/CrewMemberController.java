package edu.tcu.cs.backend.crewmember;

import edu.tcu.cs.backend.crewmember.dto.CrewMemberRequestDTO;
import edu.tcu.cs.backend.crewmember.dto.CrewMemberSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.tcu.cs.backend.crewmember.dto.CrewMemberRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

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
    
    @GetMapping("")
    public ResponseEntity<List<CrewMemberSummaryDTO>> getAllCrewMembers() {
        List<CrewMemberSummaryDTO> summaries = crewMemberService.getAllCrewMembers().stream().map(member -> {
            CrewMemberSummaryDTO dto = new CrewMemberSummaryDTO();
            dto.setFirstName(member.getFirstName());
            dto.setLastName(member.getLastName());
            dto.setEmail(member.getEmail());
            dto.setRole(member.getRole());
            dto.setQualifiedPosition(member.getQualifiedPosition());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(summaries);
    }
}
