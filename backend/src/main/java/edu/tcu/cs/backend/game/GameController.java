package edu.tcu.cs.backend.game;

import org.springframework.web.bind.annotation.*;
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
}
