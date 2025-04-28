package edu.tcu.cs.backend.game;

import edu.tcu.cs.backend.game.dto.GameDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameDTO> getAllGames() {
        return gameRepository.findAll().stream()
                .map(game -> new GameDTO(
                        game.getId(),
                        game.getDate(),
                        game.getTime(),
                        game.getVenue(),
                        game.getOpponent(),
                        game.getSport(),
                        game.getCrewPositions() // ✅ ADD THIS MISSING LINE
                ))
                .collect(Collectors.toList());
    }
}
