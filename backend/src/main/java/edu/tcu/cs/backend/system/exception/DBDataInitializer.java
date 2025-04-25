package edu.tcu.cs.backend.system.exception;

import edu.tcu.cs.backend.game.Game;
import edu.tcu.cs.backend.game.GameRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

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
            g1.setSport("Football"); // THIS LINE!
            g1.setGameDate(LocalDate.parse("2025-04-15"));
            g1.setGameTime(LocalTime.parse("16:00"));
            g1.setVenue("Test Stadium");
            g1.setOpponent("Test Opponent");
            g1.setRequiredCrewPositions(Arrays.asList("Camera", "Announcer"));

            gameRepository.save(g1);
        }

            Game g2 = new Game();
            g2.setSport("Soccer");
            g2.setGameDate(LocalDate.parse("2025-05-01"));
            g2.setGameTime(LocalTime.parse("18:30"));
            g2.setVenue("Panther Stadium");
            g2.setOpponent("SMU");
            g2.setRequiredCrewPositions(Arrays.asList("Producer", "Scorekeeper"));

            gameRepository.save(g2);
        }
    }
