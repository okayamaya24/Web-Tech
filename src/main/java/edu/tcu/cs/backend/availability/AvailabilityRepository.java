package edu.tcu.cs.backend.availability;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByGameId(Long gameId);
    List<Availability> findByCrewMemberId(Long crewMemberId);
    Optional<Availability> findByCrewMemberIdAndGameId(Long crewMemberId, Long gameId);
    
    @Modifying
    @Transactional
    void deleteByCrewMemberId(Long crewMemberId);
}
