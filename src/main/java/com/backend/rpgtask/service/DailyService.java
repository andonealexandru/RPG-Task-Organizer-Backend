package com.backend.rpgtask.service;

import com.backend.rpgtask.shared.dto.DailyDto;
import com.backend.rpgtask.shared.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DailyService {
    DailyDto createTask(DailyDto toDoDto);
    List<DailyDto> getTasks(UserDto userDto);
    void updateTask(DailyDto toDoDto, UserDto userDto);
    void deleteTask(Long id, UserDto userDto);
}
