package edu.tcu.cs.frogcrewproject.respository;

import edu.tcu.cs.frogcrewproject.model.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewMemberRespository extends JpaRepository<CrewMember, Long> {
}
