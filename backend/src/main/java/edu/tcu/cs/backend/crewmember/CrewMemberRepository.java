package edu.tcu.cs.backend.crewmember;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrewMemberRepository extends JpaRepository<CrewMember,Long> {
    boolean existsByEmail(String email);
    Optional<CrewMember> findByEmail(String email);
}
