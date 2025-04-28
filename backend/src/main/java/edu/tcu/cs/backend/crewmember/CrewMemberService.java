package edu.tcu.cs.backend.crewmember;

import edu.tcu.cs.backend.crewmember.dto.CrewMemberRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrewMemberService {

    private final CrewMemberRepository crewMemberRepository;

    public CrewMemberService(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    public CrewMemberDto registerCrewMember(CrewMemberDto crewMemberDto) {
        CrewMember crewMember = new CrewMember();
        crewMember.setFirstName(crewMemberDto.getFirstName());
        crewMember.setLastName(crewMemberDto.getLastName());
        crewMember.setEmail(crewMemberDto.getEmail());
        crewMember.setPhoneNumber(crewMemberDto.getPhoneNumber());
        crewMember.setPassword(crewMemberDto.getPassword());
        crewMember.setRole(crewMemberDto.getRole());
        crewMember.setQualifiedPosition(crewMemberDto.getQualifiedPosition());
        crewMemberRepository.save(crewMember);
        return CrewMemberMapper.toDto(crewMember);
    }

    public CrewMemberDto findByEmail(String email) {
        CrewMember crewMember = crewMemberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Crew Member not found with email: " + email));
        return CrewMemberMapper.toDto(crewMember);
    }
}
