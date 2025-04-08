package edu.tcu.cs.frogcrewproject.controller;

import edu.tcu.cs.frogcrewproject.model.CrewMember;
import edu.tcu.cs.frogcrewproject.respository.CrewMemberRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/crew")
public class CrewMemberController {

    @Autowired
    private final CrewMemberRespository crewMemberRepo;

    public CrewMemberController(CrewMemberRespository repo) {
        this.crewMemberRepo = repo;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrewMember> getCrewById(@PathVariable Long id) {
        Optional<CrewMember> crew = crewMemberRepo.findById(id);
        return crew.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public CrewMember create(@RequestBody CrewMember crew) {
        return crewMemberRepo.save(crew);
    }
}
