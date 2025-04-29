package edu.tcu.cs.backend.schedule;

import edu.tcu.cs.backend.schedule.dto.GameScheduleDto;

public class GameScheduleMapper {
    
    public static GameScheduleDto toDto(GameSchedule gameSchedule) {
        return new GameScheduleDto(
            gameSchedule.getId(),
            gameSchedule.getName(),
            gameSchedule.getDescription(),
            gameSchedule.getSeason(),
            gameSchedule.getSport(),
            gameSchedule.getGameIds()
        );
    }
    
    public static GameSchedule toEntity(GameScheduleDto dto) {
        GameSchedule gameSchedule = new GameSchedule();
        gameSchedule.setId(dto.getId());
        gameSchedule.setName(dto.getName());
        gameSchedule.setDescription(dto.getDescription());
        gameSchedule.setSeason(dto.getSeason());
        gameSchedule.setSport(dto.getSport());
        gameSchedule.setGameIds(dto.getGameIds());
        return gameSchedule;
    }
} 