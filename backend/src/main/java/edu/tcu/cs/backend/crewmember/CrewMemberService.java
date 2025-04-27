package edu.tcu.cs.backend.crewmember;

import edu.tcu.cs.backend.crewmember.dto.CrewMemberRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrewMemberService {

    private final CrewMemberRepository crewMemberRepository;

    @Autowired
    public CrewMemberService(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    public CrewMember createCrewMember(CrewMemberRequestDTO crewMemberRequestDTO) {
        // Optional: Check if email already exists
        if (crewMemberRepository.findByEmail(crewMemberRequestDTO.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already exists!");
        }

        // Map DTO to Entity
        CrewMember crewMember = new CrewMember();
        crewMember.setFirstName(crewMemberRequestDTO.getFirstName());
        crewMember.setLastName(crewMemberRequestDTO.getLastName());
        crewMember.setEmail(crewMemberRequestDTO.getEmail());
        crewMember.setPhoneNumber(crewMemberRequestDTO.getPhoneNumber());
        crewMember.setPassword(crewMemberRequestDTO.getPassword());
        crewMember.setRole(crewMemberRequestDTO.getRole());
        crewMember.setQualifiedPosition(crewMemberRequestDTO.getQualifiedPosition());

        return crewMemberRepository.save(crewMember);
    }

    public CrewMember getCrewMemberByEmail(String email) {
        return crewMemberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Crew member not found with email: " + email));
    }

    public CrewMember updateCrewMember(String email, CrewMember updatedCrewMember) {
        CrewMember existingCrewMember = crewMemberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Crew member not found with email: " + email));

        existingCrewMember.setFirstName(updatedCrewMember.getFirstName());
        existingCrewMember.setLastName(updatedCrewMember.getLastName());
        existingCrewMember.setPhoneNumber(updatedCrewMember.getPhoneNumber());
        existingCrewMember.setRole(updatedCrewMember.getRole());
        existingCrewMember.setQualifiedPosition(updatedCrewMember.getQualifiedPosition());

        return crewMemberRepository.save(existingCrewMember);
    }
}
