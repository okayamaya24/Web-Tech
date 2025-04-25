package edu.tcu.cs.backend.game;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class GameController {

    private final GameRepository gameRepository;

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/schedule")
    public List<GameDTO> getAllGames() {
        return gameRepository.findAll().stream().map(game -> {
            GameDTO dto = new GameDTO();
            dto.setId(game.getId());
            dto.setSport(game.getSport());
            dto.setVenue(game.getVenue());
            dto.setOpponent(game.getOpponent());
            dto.setCrew(game.getRequiredCrewPositions());

            // Combine gameDate and gameTime into a single datetime string
            if (game.getGameDate() != null && game.getGameTime() != null) {
                LocalDateTime dt = LocalDateTime.of(game.getGameDate(), game.getGameTime());
                dto.setDatetime(dt.toString()); // ISO 8601 format: 2025-04-22T15:00:00
            } else {
                dto.setDatetime(null);
            }

            return dto;
        }).collect(Collectors.toList());
    }

     @PostMapping("/schedule")
    public ResponseEntity<?> createGames(@RequestBody List<GameDTO> newGames) {
        List<Game> savedGames = newGames.stream().map(dto -> {
            Game game = new Game();
            game.setSport(dto.getSport());
            game.setVenue(dto.getVenue());
            game.setOpponent(dto.getOpponent());

            try {
                String[] dateTimeParts = dto.getDatetime().split("T");
                game.setGameDate(LocalDate.parse(dateTimeParts[0]));
                game.setGameTime(LocalTime.parse(dateTimeParts[1]));
            } catch (Exception e) {
                throw new RuntimeException("Invalid datetime format: " + dto.getDatetime());
            }

            game.setRequiredCrewPositions(dto.getCrew());
            return game;
        }).map(gameRepository::save).toList();

        return ResponseEntity.ok(savedGames);
    }
}
