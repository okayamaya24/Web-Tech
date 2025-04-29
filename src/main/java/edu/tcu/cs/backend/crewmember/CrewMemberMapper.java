package edu.tcu.cs.backend.crewmember;

public class CrewMemberMapper {

    public static CrewMemberDto toDto(CrewMember crewMember) {
        CrewMemberDto dto = new CrewMemberDto();
        dto.setFirstName(crewMember.getFirstName());
        dto.setLastName(crewMember.getLastName());
        dto.setEmail(crewMember.getEmail());
        dto.setPhoneNumber(crewMember.getPhoneNumber());
        dto.setPassword(crewMember.getPassword());
        dto.setRole(crewMember.getRole());
        dto.setQualifiedPosition(crewMember.getQualifiedPosition());
        return dto;
    }
}
