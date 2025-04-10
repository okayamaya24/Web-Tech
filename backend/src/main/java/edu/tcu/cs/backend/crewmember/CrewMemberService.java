package edu.tcu.cs.backend.crewmember;

import edu.tcu.cs.backend.dto.CrewMemberRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public class CrewMemberService {

    private final CrewMemberRepository crewMemberRepository;

    public CrewMemberService(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    public void registerCrewMember(CrewMemberRegistrationRequest request) {
        if (crewMemberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("A crew member with this email already exists.");
        }

        CrewMember newMember = new CrewMember();
        newMember.setFirstName(request.getFirstName());
        newMember.setLastName(request.getLastName());
        newMember.setEmail(request.getEmail());
        newMember.setPhoneNumber(request.getPhoneNumber());
        newMember.setPassword(request.getPassword()); // hash in production!
        newMember.setRole(request.getRole());
        newMember.setQualifiedPosition(request.getQualifiedPosition());

        crewMemberRepository.save(newMember);
    }

    public CrewMember getCrewMemberById(Long id) {
        return crewMemberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Crew member not found with ID: " + id));
    }
}