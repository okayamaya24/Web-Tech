package edu.tcu.cs.backend.game;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "http://localhost:5173")
public class GameController {

    private final GameRepository gameRepository; // ADD THIS FIELD

    public GameController(GameRepository gameRepository) { // Constructor injection
        this.gameRepository = gameRepository;
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @PostMapping
    public Game createGame(@RequestBody Game game) {
        return gameRepository.save(game);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameRepository.deleteById(id);
    }
}
