package edu.tcu.cs.backend.crewmember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {

    boolean existsByEmail(String email); // You already have this one
    Optional<CrewMember> findByEmail(String email);
}