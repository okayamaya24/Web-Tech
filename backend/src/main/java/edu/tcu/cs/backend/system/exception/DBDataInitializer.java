package edu.tcu.cs.backend.system;

import edu.tcu.cs.backend.game.Game;
import edu.tcu.cs.backend.game.GameRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DBDataInitializer {

    private final GameRepository gameRepository;

    public DBDataInitializer(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @PostConstruct
    public void init() {
        if (gameRepository.count() == 0) {
            Game g1 = new Game();
            g1.setGameDate(LocalDate.of(2025, 4, 12));
            g1.setGameTime(LocalTime.of(18, 30));
            g1.setOpponent("Dallas Knights");
            g1.setVenue("TCU Stadium");
            g1.setRequiredCrewPositions("Referee, Line Judge");

            Game g2 = new Game();
            g2.setGameDate(LocalDate.of(2025, 4, 15));
            g2.setGameTime(LocalTime.of(16, 0));
            g2.setOpponent("Fort Worth Flames");
            g2.setVenue("Panther Field");
            g2.setRequiredCrewPositions("Scorekeeper, Referee");

            gameRepository.save(g1);
            gameRepository.save(g2);
        }
    }
}
