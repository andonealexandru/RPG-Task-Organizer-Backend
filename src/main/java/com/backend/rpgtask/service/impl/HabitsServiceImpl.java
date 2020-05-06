package com.backend.rpgtask.service.impl;

import com.backend.rpgtask.io.entity.HabitsEntity;
import com.backend.rpgtask.io.entity.UserEntity;
import com.backend.rpgtask.io.repositories.HabitsRepository;
import com.backend.rpgtask.io.repositories.UserRepository;
import com.backend.rpgtask.service.HabitsService;
import com.backend.rpgtask.shared.dto.HabitsDto;
import com.backend.rpgtask.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HabitsServiceImpl implements HabitsService {

    @Autowired
    HabitsRepository habitsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public HabitsDto createTask(HabitsDto habitsDto) {

        HabitsEntity habitsEntity = modelMapper.map(habitsDto, HabitsEntity.class);
        habitsEntity.setUserId(userRepository.findByUserId(habitsDto.getUserId()));

        HabitsEntity storedEntity = habitsRepository.save(habitsEntity);

        return modelMapper.map(storedEntity, HabitsDto.class);
    }

    @Override
    public List<HabitsDto> getTasks(UserDto userDto) {

        UserEntity userEntity = userRepository.findByUserId(userDto.getUserId());
        List<HabitsEntity> habitsEntities = habitsRepository.findByUserIdOrderByOrderNumber(userEntity);

        List<HabitsDto> response = new ArrayList<>();

        for (int i = 0; i < habitsEntities.size(); ++i) {
            response.add(modelMapper.map(habitsEntities.get(i), HabitsDto.class));
        }

        return response;
    }

    @Override
    public void updateTask(HabitsDto habitsDto, UserDto userDto) {
        HabitsEntity habitsEntity = habitsRepository.findById(habitsDto.getId()).get();

        habitsEntity.setOrderNumber(habitsDto.getOrderNumber());
        habitsEntity.setTaskTitle(habitsDto.getTaskTitle());
        habitsEntity.setTaskText(habitsDto.getTaskText());

        habitsRepository.save(habitsEntity);
    }

    @Override
    public void deleteTask(Long id, UserDto userDto) {

        HabitsEntity habitsEntity = habitsRepository.findById(id).get();

        habitsRepository.delete(habitsEntity);
    }
}
