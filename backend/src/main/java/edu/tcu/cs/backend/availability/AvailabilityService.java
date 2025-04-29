package edu.tcu.cs.backend.availability;

import edu.tcu.cs.backend.game.Game;
import edu.tcu.cs.backend.game.GameRepository;
import edu.tcu.cs.backend.crewmember.CrewMember;
import edu.tcu.cs.backend.crewmember.CrewMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AvailabilityService {

    private final CrewMemberRepository crewMemberRepository;
    private final GameRepository gameRepository;
    private final AvailabilityRepository availabilityRepository;

    public AvailabilityService(CrewMemberRepository crewMemberRepository,
                               GameRepository gameRepository,
                               AvailabilityRepository availabilityRepository) {
        this.crewMemberRepository = crewMemberRepository;
        this.gameRepository = gameRepository;
        this.availabilityRepository = availabilityRepository;
    }

    public void submitAvailability(Long gameId, String userEmail, AvailabilityRequestDTO request) {
        // Confirm that crew member and game actually exist
        CrewMember crewMember = crewMemberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Crew member not found"));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        // Create a new Availability entry
        Availability availability = new Availability();
        availability.setCrewMemberEmail(crewMember.getEmail());
        availability.setGameId(game.getId());
        availability.setAvailable(request.getAvailable());
        availability.setComment(request.getComment());

        availabilityRepository.save(availability);
    }
}
