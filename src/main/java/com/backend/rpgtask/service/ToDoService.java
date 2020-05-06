package com.backend.rpgtask.service;

import com.backend.rpgtask.shared.dto.ToDoDto;
import com.backend.rpgtask.shared.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ToDoService {
    ToDoDto createTask(ToDoDto toDoDto);
    List<ToDoDto> getTasks(UserDto userDto);
    void updateTask(ToDoDto toDoDto, UserDto userDto);
    void deleteTask(Long id, UserDto userDto);
}
