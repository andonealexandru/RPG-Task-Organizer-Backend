package com.backend.rpgtask.service.impl;

import com.backend.rpgtask.io.entity.ToDoEntity;
import com.backend.rpgtask.io.entity.UserEntity;
import com.backend.rpgtask.io.repositories.ToDoRepository;
import com.backend.rpgtask.io.repositories.UserRepository;
import com.backend.rpgtask.service.ToDoService;
import com.backend.rpgtask.shared.dto.ToDoDto;
import com.backend.rpgtask.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService {

    @Autowired
    ToDoRepository toDoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ToDoDto createTask(ToDoDto toDoDto) {

        ToDoEntity toDoEntity = modelMapper.map(toDoDto, ToDoEntity.class);
        toDoEntity.setUserId(userRepository.findByUserId(toDoDto.getUserId()));

        ToDoEntity storedEntity = toDoRepository.save(toDoEntity);

        return modelMapper.map(storedEntity, ToDoDto.class);
    }

    @Override
    public List<ToDoDto> getTasks(UserDto userDto) {

        UserEntity userEntity = userRepository.findByUserId(userDto.getUserId());
        List<ToDoEntity> toDoEntities = toDoRepository.findByUserIdOrderByOrderNumber(userEntity);

        List<ToDoDto> response = new ArrayList<>();

        for (int i = 0; i < toDoEntities.size(); ++i) {
            response.add(modelMapper.map(toDoEntities.get(i), ToDoDto.class));
        }

        return response;
    }

    @Override
    public void updateTask(ToDoDto toDoDto, UserDto userDto) {

        ToDoEntity toDoEntity = toDoRepository.findById(toDoDto.getId()).get();

        toDoEntity.setOrderNumber(toDoDto.getOrderNumber());
        toDoEntity.setTaskTitle(toDoDto.getTaskTitle());
        toDoEntity.setTaskText(toDoDto.getTaskText());

        toDoRepository.save(toDoEntity);
    }

    @Override
    public void deleteTask(Long id, UserDto userDto) {

        ToDoEntity toDoEntity = toDoRepository.findById(id).get();

        toDoRepository.delete(toDoEntity);
    }
}
