package edu.tcu.cs.backend.availability;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    // Later we can add custom queries like findByCrewMemberEmail if needed
}
