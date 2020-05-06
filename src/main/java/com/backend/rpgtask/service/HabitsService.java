package com.backend.rpgtask.service;

import com.backend.rpgtask.shared.dto.HabitsDto;
import com.backend.rpgtask.shared.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HabitsService {
    HabitsDto createTask(HabitsDto toDoDto);
    List<HabitsDto> getTasks(UserDto userDto);
    void updateTask(HabitsDto toDoDto, UserDto userDto);
    void deleteTask(Long id, UserDto userDto);
}
