package edu.tcu.cs.backend.crewmember;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewMemberRepository extends JpaRepository<CrewMember,Long> {
    boolean existsByEmail(String email);
}
