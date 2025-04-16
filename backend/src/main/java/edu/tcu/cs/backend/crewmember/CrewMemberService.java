package edu.tcu.cs.backend.crewmember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.tcu.cs.backend.crewmember.dto.CrewMemberRequestDTO;

@Service
public class CrewMemberService {

    private final CrewMemberRepository crewMemberRepository;

    @Autowired
    public CrewMemberService(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    public CrewMember createProfile(CrewMemberRequestDTO dto) {
        if (crewMemberRepository.existsByEmail(dto.email)) {
            throw new IllegalArgumentException("Email already in use.");
        }

        CrewMember crewMember = new CrewMember();
        crewMember.setFirstName(dto.firstName);
        crewMember.setLastName(dto.lastName);
        crewMember.setEmail(dto.email);
        crewMember.setPhoneNumber(dto.phoneNumber);
        crewMember.setPassword(dto.password);
        crewMember.setRole(dto.role);
        crewMember.setQualifiedPosition(dto.qualifiedPosition);

        return crewMemberRepository.save(crewMember);
    }

    public CrewMember findByEmail(String email) {
        return crewMemberRepository.findByEmail(email).orElse(null);
    }
}
