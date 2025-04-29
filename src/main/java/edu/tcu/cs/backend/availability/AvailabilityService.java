package edu.tcu.cs.backend.availability;

import edu.tcu.cs.backend.game.Game;
import edu.tcu.cs.backend.game.GameRepository;
import edu.tcu.cs.backend.crewmember.CrewMember;
import edu.tcu.cs.backend.crewmember.CrewMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class AvailabilityService {

    private static final Logger logger = Logger.getLogger(AvailabilityService.class.getName());
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

    public Availability submitAvailability(Long gameId, Long crewMemberId, AvailabilityRequestDTO request) {
        // Find or create availability record
        Availability availability = availabilityRepository.findByCrewMemberIdAndGameId(crewMemberId, gameId)
                .orElse(new Availability(crewMemberId, gameId, request.getAvailable(), request.getComment()));
        
        // Update if it exists
        availability.setAvailable(request.getAvailable());
        availability.setComment(request.getComment());
        
        // Save and return
        return availabilityRepository.save(availability);
    }
    
    public Availability submitAvailabilityByEmail(Long gameId, String crewMemberEmail, AvailabilityRequestDTO request) {
        // Validate inputs
        if (gameId == null) {
            throw new IllegalArgumentException("Game ID is required");
        }
        if (crewMemberEmail == null || crewMemberEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Crew member email is required");
        }
        
        // Verify the game exists
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with ID: " + gameId));
        
        // Find the crew member by email
        CrewMember crewMember = crewMemberRepository.findByEmail(crewMemberEmail)
                .orElseThrow(() -> new RuntimeException("Crew member not found with email: " + crewMemberEmail));
        
        // Submit availability using ID
        return submitAvailability(gameId, crewMember.getId(), request);
    }
    
    public List<Map<String, Object>> getUpcomingGamesForAvailability(String crewMemberEmail) {
        // Validate email
        if (crewMemberEmail == null || crewMemberEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Crew member email is required");
        }
        
        // Find the crew member by email
        CrewMember crewMember = crewMemberRepository.findByEmail(crewMemberEmail)
                .orElseThrow(() -> new RuntimeException("Crew member not found with email: " + crewMemberEmail));
        
        // Get all games
        List<Game> allGames = gameRepository.findAll();
        
        // Filter for upcoming games
        LocalDate today = LocalDate.now();
        List<Game> upcomingGames = allGames.stream()
                .filter(game -> {
                    if (game.getDate() == null) return false;
                    try {
                        LocalDate gameDate = LocalDate.parse(game.getDate());
                        return !gameDate.isBefore(today);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .sorted((g1, g2) -> {
                    LocalDate date1 = LocalDate.parse(g1.getDate());
                    LocalDate date2 = LocalDate.parse(g2.getDate());
                    return date1.compareTo(date2);
                })
                .collect(Collectors.toList());
        
        // Get existing availability for this crew member
        List<Availability> availabilities = availabilityRepository.findByCrewMemberId(crewMember.getId());
        Map<Long, Availability> availabilityMap = new HashMap<>();
        for (Availability availability : availabilities) {
            availabilityMap.put(availability.getGameId(), availability);
        }
        
        // Create response with game details and availability status
        List<Map<String, Object>> result = new ArrayList<>();
        for (Game game : upcomingGames) {
            Availability availability = availabilityMap.get(game.getId());
            boolean hasSubmitted = availability != null;
            
            Map<String, Object> gameInfo = new HashMap<>();
            gameInfo.put("gameId", game.getId());
            gameInfo.put("date", game.getDate());
            gameInfo.put("time", game.getTime());
            gameInfo.put("venue", game.getVenue());
            gameInfo.put("opponent", game.getOpponent());
            gameInfo.put("sport", game.getSport());
            gameInfo.put("hasSubmittedAvailability", hasSubmitted);
            
            if (hasSubmitted) {
                gameInfo.put("isAvailable", availability.isAvailable());
                gameInfo.put("comment", availability.getComment());
            }
            
            result.add(gameInfo);
        }
        
        return result;
    }
    
    public List<Availability> getAvailabilityByGameId(Long gameId) {
        return availabilityRepository.findByGameId(gameId);
    }
    
    public List<Availability> getAvailabilityByCrewMemberId(Long crewMemberId) {
        return availabilityRepository.findByCrewMemberId(crewMemberId);
    }
    
    public List<Availability> getAvailabilityByCrewMemberEmail(String crewMemberEmail) {
        // Validate email
        if (crewMemberEmail == null || crewMemberEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Crew member email is required");
        }
        
        // Find the crew member by email
        CrewMember crewMember = crewMemberRepository.findByEmail(crewMemberEmail)
                .orElseThrow(() -> new RuntimeException("Crew member not found with email: " + crewMemberEmail));
        
        // Get availability
        return availabilityRepository.findByCrewMemberId(crewMember.getId());
    }
    
    public Availability getSpecificAvailability(Long gameId, Long crewMemberId) {
        return availabilityRepository.findByCrewMemberIdAndGameId(crewMemberId, gameId)
                .orElseThrow(() -> new RuntimeException("Availability not found for this crew member and game"));
    }
    
    public Availability getSpecificAvailabilityByEmail(Long gameId, String crewMemberEmail) {
        // Validate inputs
        if (gameId == null) {
            throw new IllegalArgumentException("Game ID is required");
        }
        if (crewMemberEmail == null || crewMemberEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Crew member email is required");
        }
        
        // Find the crew member by email
        CrewMember crewMember = crewMemberRepository.findByEmail(crewMemberEmail)
                .orElseThrow(() -> new RuntimeException("Crew member not found with email: " + crewMemberEmail));
        
        // Get availability
        return getSpecificAvailability(gameId, crewMember.getId());
    }

    public void deleteAvailability(Long gameId, Long crewMemberId) {
        Availability availability = availabilityRepository.findByCrewMemberIdAndGameId(crewMemberId, gameId)
                .orElseThrow(() -> new RuntimeException("Availability not found"));
        
        availabilityRepository.delete(availability);
    }
    
    public void deleteAvailabilityByEmail(Long gameId, String crewMemberEmail) {
        // Validate inputs
        if (gameId == null) {
            throw new IllegalArgumentException("Game ID is required");
        }
        if (crewMemberEmail == null || crewMemberEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Crew member email is required");
        }
        
        // Find the crew member by email
        CrewMember crewMember = crewMemberRepository.findByEmail(crewMemberEmail)
                .orElseThrow(() -> new RuntimeException("Crew member not found with email: " + crewMemberEmail));
        
        // Delete availability
        deleteAvailability(gameId, crewMember.getId());
    }
}
