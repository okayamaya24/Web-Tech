package edu.tcu.cs.backend.schedule;

import edu.tcu.cs.backend.game.Game;
import edu.tcu.cs.backend.game.GameRepository;
import edu.tcu.cs.backend.schedule.dto.GameScheduleDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class GameScheduleService {
    
    private static final Logger logger = Logger.getLogger(GameScheduleService.class.getName());
    private final GameScheduleRepository gameScheduleRepository;
    private final GameRepository gameRepository;
    
    public GameScheduleService(GameScheduleRepository gameScheduleRepository, GameRepository gameRepository) {
        this.gameScheduleRepository = gameScheduleRepository;
        this.gameRepository = gameRepository;
    }
    
    public GameScheduleDto createSchedule(GameScheduleDto dto) {
        // Validate input
        if (dto == null) {
            throw new IllegalArgumentException("Game schedule data is required");
        }
        
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Schedule name is required");
        }
        
        if (dto.getSeason() == null || dto.getSeason().trim().isEmpty()) {
            throw new IllegalArgumentException("Season is required");
        }
        
        // Check if a schedule with the same name already exists
        List<GameSchedule> existingSchedules = gameScheduleRepository.findAll();
        boolean nameExists = existingSchedules.stream()
                .anyMatch(schedule -> schedule.getName().equalsIgnoreCase(dto.getName()));
                
        if (nameExists) {
            throw new RuntimeException("A schedule with this name already exists");
        }
        
        // Create and save the new schedule
        GameSchedule gameSchedule = GameScheduleMapper.toEntity(dto);
        GameSchedule savedSchedule = gameScheduleRepository.save(gameSchedule);
        
        logger.info("Created new game schedule: " + savedSchedule.getName() + " for season: " + savedSchedule.getSeason());
        
        return GameScheduleMapper.toDto(savedSchedule);
    }
    
    public List<GameScheduleDto> getAllSchedules() {
        return gameScheduleRepository.findAll().stream()
                .map(GameScheduleMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public GameScheduleDto getScheduleById(Long id) {
        GameSchedule gameSchedule = gameScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + id));
        return GameScheduleMapper.toDto(gameSchedule);
    }
    
    public GameScheduleDto addGameToSchedule(Long scheduleId, Long gameId) {
        // Validate input
        if (scheduleId == null) {
            throw new IllegalArgumentException("Schedule ID is required");
        }
        
        if (gameId == null) {
            throw new IllegalArgumentException("Game ID is required");
        }
        
        // Verify that the game exists
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameId));
        
        // Verify that the schedule exists
        GameSchedule schedule = gameScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + scheduleId));
        
        // Check if the game is already in the schedule
        if (schedule.getGameIds().contains(gameId)) {
            throw new RuntimeException("Game is already in this schedule");
        }
        
        // Add the game to the schedule
        schedule.addGameId(gameId);
        GameSchedule updatedSchedule = gameScheduleRepository.save(schedule);
        
        logger.info("Added game (ID: " + gameId + ") to schedule: " + schedule.getName());
        
        return GameScheduleMapper.toDto(updatedSchedule);
    }
    
    public GameScheduleDto removeGameFromSchedule(Long scheduleId, Long gameId) {
        // Verify that the schedule exists
        GameSchedule schedule = gameScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + scheduleId));
        
        // Remove the game from the schedule
        schedule.removeGameId(gameId);
        GameSchedule updatedSchedule = gameScheduleRepository.save(schedule);
        
        return GameScheduleMapper.toDto(updatedSchedule);
    }
    
    public void deleteSchedule(Long scheduleId) {
        if (!gameScheduleRepository.existsById(scheduleId)) {
            throw new RuntimeException("Schedule not found with id: " + scheduleId);
        }
        gameScheduleRepository.deleteById(scheduleId);
    }
} 