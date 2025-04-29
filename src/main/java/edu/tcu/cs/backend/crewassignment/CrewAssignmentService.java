package edu.tcu.cs.backend.crewassignment;

import edu.tcu.cs.backend.availability.Availability;
import edu.tcu.cs.backend.availability.AvailabilityRepository;
import edu.tcu.cs.backend.crewassignment.dto.CrewAssignmentDto;
import edu.tcu.cs.backend.crewassignment.dto.MultipleAssignmentsDto;
import edu.tcu.cs.backend.crewmember.CrewMember;
import edu.tcu.cs.backend.crewmember.CrewMemberRepository;
import edu.tcu.cs.backend.game.Game;
import edu.tcu.cs.backend.game.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

@Service
@Transactional
public class CrewAssignmentService {

    private static final Logger logger = Logger.getLogger(CrewAssignmentService.class.getName());
    private final CrewAssignmentRepository crewAssignmentRepository;
    private final GameRepository gameRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final AvailabilityRepository availabilityRepository;

    @Autowired
    public CrewAssignmentService(CrewAssignmentRepository crewAssignmentRepository,
                                GameRepository gameRepository,
                                CrewMemberRepository crewMemberRepository,
                                AvailabilityRepository availabilityRepository) {
        this.crewAssignmentRepository = crewAssignmentRepository;
        this.gameRepository = gameRepository;
        this.crewMemberRepository = crewMemberRepository;
        this.availabilityRepository = availabilityRepository;
    }

    public CrewAssignment save(CrewAssignment crewAssignment) {
        return crewAssignmentRepository.save(crewAssignment);
    }
    
    public List<CrewAssignment> getCrewAssignmentsByGameId(Long gameId) {
        return crewAssignmentRepository.findByGameId(gameId);
    }
    
    public List<CrewAssignmentDto> getCrewAssignmentDtosByGameId(Long gameId) {
        return crewAssignmentRepository.findByGameId(gameId).stream()
                .map(CrewAssignmentMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public CrewAssignmentDto createCrewAssignment(CrewAssignmentDto dto) {
        // Validate input
        if (dto == null) {
            throw new IllegalArgumentException("Crew assignment data is required");
        }
        
        if (dto.getGameId() == null) {
            throw new IllegalArgumentException("Game ID is required");
        }
        
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Crew member email/name is required");
        }
        
        if (dto.getPosition() == null || dto.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("Position is required");
        }
        
        // Verify that the game exists
        Game game = gameRepository.findById(dto.getGameId())
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + dto.getGameId()));
        
        // Verify that the crew member exists
        CrewMember crewMember = crewMemberRepository.findByEmail(dto.getName())
                .orElseThrow(() -> new RuntimeException("Crew member not found with email: " + dto.getName()));
                
        // Verify that the position is valid for the game
        if (!game.getCrewPositions().contains(dto.getPosition())) {
            throw new RuntimeException("Position " + dto.getPosition() + " is not available for this game");
        }
        
        // Verify that the crew member has the required qualifications
        if (!crewMember.getQualifiedPosition().equals(dto.getPosition())) {
            throw new RuntimeException("Crew member is not qualified for position: " + dto.getPosition());
        }
        
        // Check if the crew member is available for this game
        List<Availability> availabilities = availabilityRepository.findByCrewMemberId(crewMember.getId());
        boolean isAvailable = availabilities.stream()
                .filter(a -> a.getGameId().equals(dto.getGameId()))
                .anyMatch(Availability::isAvailable);
                
        if (!isAvailable) {
            throw new RuntimeException("Crew member is not available for this game");
        }
        
        // Check if this position is already assigned for this game
        List<CrewAssignment> existingAssignments = crewAssignmentRepository.findByGameId(dto.getGameId());
        boolean positionAlreadyAssigned = existingAssignments.stream()
                .anyMatch(a -> a.getPosition().equals(dto.getPosition()));
                
        if (positionAlreadyAssigned) {
            throw new RuntimeException("Position " + dto.getPosition() + " is already assigned for this game");
        }
        
        // Check if crew member is already assigned to this game
        boolean crewMemberAlreadyAssigned = existingAssignments.stream()
                .anyMatch(a -> a.getName().equals(dto.getName()));
                
        if (crewMemberAlreadyAssigned) {
            throw new RuntimeException("Crew member is already assigned to this game in another position");
        }
        
        // Save the crew assignment
        CrewAssignment crewAssignment = CrewAssignmentMapper.toEntity(dto);
        CrewAssignment savedAssignment = crewAssignmentRepository.save(crewAssignment);
        
        logger.info(String.format("Created crew assignment: %s assigned to position %s for game %d", 
                crewMember.getEmail(), dto.getPosition(), dto.getGameId()));
        
        return CrewAssignmentMapper.toDto(savedAssignment);
    }
    
    public List<CrewAssignmentDto> createMultipleCrewAssignments(MultipleAssignmentsDto multipleAssignmentsDto) {
        // Validate input
        if (multipleAssignmentsDto == null) {
            throw new IllegalArgumentException("Multiple assignment data is required");
        }
        
        if (multipleAssignmentsDto.getGameId() == null) {
            throw new IllegalArgumentException("Game ID is required");
        }
        
        if (multipleAssignmentsDto.getAssignments() == null || multipleAssignmentsDto.getAssignments().isEmpty()) {
            throw new IllegalArgumentException("At least one assignment is required");
        }
        
        List<CrewAssignmentDto> createdAssignments = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        
        for (CrewAssignmentDto assignmentDto : multipleAssignmentsDto.getAssignments()) {
            // Set the gameId from the parent object
            assignmentDto.setGameId(multipleAssignmentsDto.getGameId());
            
            try {
                CrewAssignmentDto createdAssignment = createCrewAssignment(assignmentDto);
                createdAssignments.add(createdAssignment);
            } catch (Exception e) {
                // Collect error but continue to process the next assignment
                errors.add(String.format("Error assigning %s to %s: %s", 
                        assignmentDto.getName(), assignmentDto.getPosition(), e.getMessage()));
                logger.warning(e.getMessage());
            }
        }
        
        if (!errors.isEmpty()) {
            logger.warning("Some assignments could not be created: " + String.join(", ", errors));
        }
        
        logger.info(String.format("Created %d of %d crew assignments for game %d", 
                createdAssignments.size(), multipleAssignmentsDto.getAssignments().size(), 
                multipleAssignmentsDto.getGameId()));
        
        return createdAssignments;
    }
    
    public void deleteCrewAssignment(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Assignment ID is required");
        }
        
        if (!crewAssignmentRepository.existsById(id)) {
            throw new RuntimeException("Crew assignment not found with id: " + id);
        }
        
        crewAssignmentRepository.deleteById(id);
        logger.info("Deleted crew assignment with ID: " + id);
    }
    
    public void deleteAllAssignmentsForGame(Long gameId) {
        if (gameId == null) {
            throw new IllegalArgumentException("Game ID is required");
        }
        
        List<CrewAssignment> assignments = crewAssignmentRepository.findByGameId(gameId);
        
        if (assignments.isEmpty()) {
            logger.info("No assignments found for game ID: " + gameId);
            return;
        }
        
        crewAssignmentRepository.deleteAll(assignments);
        logger.info("Deleted " + assignments.size() + " crew assignments for game ID: " + gameId);
    }

    /**
     * Recommends crew members for a specific game based on availability and qualifications.
     * @param gameId The ID of the game
     * @return A list of recommendations by position
     */
    public List<RecommendationDTO> getRecommendedCrewForGame(Long gameId) {
        // Verify that the game exists
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameId));
        
        // Get the positions needed for this game
        List<String> positions = game.getCrewPositions();
        
        // Get all availabilities for this game
        List<Availability> availabilities = availabilityRepository.findByGameId(gameId);
        
        // Get all crew members who are available for this game
        List<Long> availableCrewMemberIds = availabilities.stream()
                .filter(Availability::isAvailable)
                .map(Availability::getCrewMemberId)
                .collect(Collectors.toList());
        
        // Get the crew members who are available
        List<CrewMember> availableCrewMembers = crewMemberRepository.findAllById(availableCrewMemberIds);
        
        // Check if any positions are already assigned
        List<CrewAssignment> existingAssignments = crewAssignmentRepository.findByGameId(gameId);
        List<String> assignedPositions = existingAssignments.stream()
                .map(CrewAssignment::getPosition)
                .collect(Collectors.toList());
        
        // Create a map of positions to assigned crew members
        Map<String, String> positionToAssignedCrewMap = new HashMap<>();
        for (CrewAssignment assignment : existingAssignments) {
            positionToAssignedCrewMap.put(assignment.getPosition(), assignment.getName());
        }
        
        // Create recommendations for each position, both assigned and unassigned
        List<RecommendationDTO> recommendations = new ArrayList<>();
        for (String position : positions) {
            RecommendationDTO recommendationDTO = new RecommendationDTO();
            recommendationDTO.setPosition(position);
            
            // Check if position is already assigned
            boolean isAssigned = assignedPositions.contains(position);
            recommendationDTO.setAssigned(isAssigned);
            
            if (isAssigned) {
                // Add the currently assigned crew member
                String assignedCrewEmail = positionToAssignedCrewMap.get(position);
                CrewMember assignedMember = crewMemberRepository.findByEmail(assignedCrewEmail)
                        .orElse(null);
                
                if (assignedMember != null) {
                    CrewMemberRecommendation current = new CrewMemberRecommendation();
                    current.setCrewMemberId(assignedMember.getId());
                    current.setName(assignedMember.getFirstName() + " " + assignedMember.getLastName());
                    current.setEmail(assignedMember.getEmail());
                    current.setPhoneNumber(assignedMember.getPhoneNumber());
                    current.setCurrentlyAssigned(true);
                    
                    recommendationDTO.setAssignedCrew(current);
                }
            }
            
            // Find qualified crew members for this position
            List<CrewMember> qualifiedMembers = availableCrewMembers.stream()
                    .filter(cm -> cm.getQualifiedPosition() != null && cm.getQualifiedPosition().equals(position))
                    .collect(Collectors.toList());
            
            List<CrewMemberRecommendation> crewRecommendations = qualifiedMembers.stream()
                    .map(cm -> {
                        CrewMemberRecommendation rec = new CrewMemberRecommendation();
                        rec.setCrewMemberId(cm.getId());
                        rec.setName(cm.getFirstName() + " " + cm.getLastName());
                        rec.setEmail(cm.getEmail());
                        rec.setPhoneNumber(cm.getPhoneNumber());
                        rec.setCurrentlyAssigned(false);
                        return rec;
                    })
                    .collect(Collectors.toList());
            
            recommendationDTO.setRecommendedCrew(crewRecommendations);
            recommendations.add(recommendationDTO);
        }
        
        return recommendations;
    }

    // Inner class for recommendation DTOs
    public static class RecommendationDTO {
        private String position;
        private boolean assigned;
        private CrewMemberRecommendation assignedCrew;
        private List<CrewMemberRecommendation> recommendedCrew = new ArrayList<>();
        
        public String getPosition() {
            return position;
        }
        
        public void setPosition(String position) {
            this.position = position;
        }
        
        public boolean isAssigned() {
            return assigned;
        }
        
        public void setAssigned(boolean assigned) {
            this.assigned = assigned;
        }
        
        public CrewMemberRecommendation getAssignedCrew() {
            return assignedCrew;
        }
        
        public void setAssignedCrew(CrewMemberRecommendation assignedCrew) {
            this.assignedCrew = assignedCrew;
        }
        
        public List<CrewMemberRecommendation> getRecommendedCrew() {
            return recommendedCrew;
        }
        
        public void setRecommendedCrew(List<CrewMemberRecommendation> recommendedCrew) {
            this.recommendedCrew = recommendedCrew;
        }
    }

    public static class CrewMemberRecommendation {
        private Long crewMemberId;
        private String name;
        private String email;
        private String phoneNumber;
        private boolean currentlyAssigned;
        
        public Long getCrewMemberId() {
            return crewMemberId;
        }
        
        public void setCrewMemberId(Long crewMemberId) {
            this.crewMemberId = crewMemberId;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getPhoneNumber() {
            return phoneNumber;
        }
        
        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
        
        public boolean isCurrentlyAssigned() {
            return currentlyAssigned;
        }
        
        public void setCurrentlyAssigned(boolean currentlyAssigned) {
            this.currentlyAssigned = currentlyAssigned;
        }
    }

    /**
     * Gets all positions required for a game, indicating which are filled and which are vacant
     * @param gameId The ID of the game
     * @return A list of position status objects
     */
    public List<PositionStatusDTO> getRequiredPositionsForGame(Long gameId) {
        // Verify the game exists
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameId));
        
        // Get all positions for this game
        List<String> allPositions = game.getCrewPositions();
        
        // Get all existing assignments
        List<CrewAssignment> assignments = crewAssignmentRepository.findByGameId(gameId);
        
        // Create a list to hold results
        List<PositionStatusDTO> positionStatuses = new ArrayList<>();
        
        // For each position, check if it's assigned
        for (String position : allPositions) {
            PositionStatusDTO status = new PositionStatusDTO();
            status.setPosition(position);
            
            // Find an assignment for this position if it exists
            CrewAssignment assignment = assignments.stream()
                    .filter(a -> a.getPosition().equals(position))
                    .findFirst()
                    .orElse(null);
            
            if (assignment != null) {
                status.setAssigned(true);
                status.setCrewMemberName(assignment.getName());
                status.setAssignmentId(assignment.getId());
            } else {
                status.setAssigned(false);
            }
            
            positionStatuses.add(status);
        }
        
        return positionStatuses;
    }

    public static class PositionStatusDTO {
        private String position;
        private boolean assigned;
        private String crewMemberName;
        private Long assignmentId;
        
        public String getPosition() {
            return position;
        }
        
        public void setPosition(String position) {
            this.position = position;
        }
        
        public boolean isAssigned() {
            return assigned;
        }
        
        public void setAssigned(boolean assigned) {
            this.assigned = assigned;
        }
        
        public String getCrewMemberName() {
            return crewMemberName;
        }
        
        public void setCrewMemberName(String crewMemberName) {
            this.crewMemberName = crewMemberName;
        }
        
        public Long getAssignmentId() {
            return assignmentId;
        }
        
        public void setAssignmentId(Long assignmentId) {
            this.assignmentId = assignmentId;
        }
    }

    /**
     * Gets upcoming games with crew assignment status
     * @return A list of games with assignment status DTOs
     */
    public List<GameWithAssignmentStatusDTO> getUpcomingGamesWithAssignmentStatus() {
        // Get all games
        List<Game> games = gameRepository.findAll();
        
        // Filter for upcoming games (games with a future date)
        java.time.LocalDate today = java.time.LocalDate.now();
        List<Game> upcomingGames = games.stream()
                .filter(game -> {
                    if (game.getDate() == null) return false;
                    try {
                        java.time.LocalDate gameDate = java.time.LocalDate.parse(game.getDate());
                        return !gameDate.isBefore(today);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .sorted((g1, g2) -> {
                    java.time.LocalDate date1 = java.time.LocalDate.parse(g1.getDate());
                    java.time.LocalDate date2 = java.time.LocalDate.parse(g2.getDate());
                    return date1.compareTo(date2);
                })
                .collect(Collectors.toList());
        
        // Create DTOs with assignment status
        List<GameWithAssignmentStatusDTO> result = new ArrayList<>();
        
        for (Game game : upcomingGames) {
            GameWithAssignmentStatusDTO dto = new GameWithAssignmentStatusDTO();
            dto.setGameId(game.getId());
            dto.setOpponent(game.getOpponent());
            dto.setDate(game.getDate());
            dto.setTime(game.getTime());
            dto.setLocation(game.getVenue());
            
            // Get required positions
            List<String> requiredPositions = game.getCrewPositions();
            dto.setTotalPositionsRequired(requiredPositions.size());
            
            // Get assignments for this game
            List<CrewAssignment> assignments = crewAssignmentRepository.findByGameId(game.getId());
            dto.setPositionsFilled(assignments.size());
            
            result.add(dto);
        }
        
        return result;
    }

    public static class GameWithAssignmentStatusDTO {
        private Long gameId;
        private String opponent;
        private String date;
        private String time;
        private String location;
        private int totalPositionsRequired;
        private int positionsFilled;
        
        public Long getGameId() {
            return gameId;
        }
        
        public void setGameId(Long gameId) {
            this.gameId = gameId;
        }
        
        public String getOpponent() {
            return opponent;
        }
        
        public void setOpponent(String opponent) {
            this.opponent = opponent;
        }
        
        public String getDate() {
            return date;
        }
        
        public void setDate(String date) {
            this.date = date;
        }
        
        public String getTime() {
            return time;
        }
        
        public void setTime(String time) {
            this.time = time;
        }
        
        public String getLocation() {
            return location;
        }
        
        public void setLocation(String location) {
            this.location = location;
        }
        
        public int getTotalPositionsRequired() {
            return totalPositionsRequired;
        }
        
        public void setTotalPositionsRequired(int totalPositionsRequired) {
            this.totalPositionsRequired = totalPositionsRequired;
        }
        
        public int getPositionsFilled() {
            return positionsFilled;
        }
        
        public void setPositionsFilled(int positionsFilled) {
            this.positionsFilled = positionsFilled;
        }
    }
}
