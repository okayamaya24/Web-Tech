package edu.tcu.cs.backend.schedule.dto;

import jakarta.validation.constraints.NotNull;

public class GameToScheduleDto {
    
    @NotNull(message = "Game ID is required.")
    private Long gameId;
    
    public GameToScheduleDto() {}
    
    public GameToScheduleDto(Long gameId) {
        this.gameId = gameId;
    }
    
    public Long getGameId() {
        return gameId;
    }
    
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
} 