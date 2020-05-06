package com.backend.rpgtask.service.impl;

import com.backend.rpgtask.io.entity.DailyEntity;
import com.backend.rpgtask.io.entity.UserEntity;
import com.backend.rpgtask.io.repositories.DailyRepository;
import com.backend.rpgtask.io.repositories.UserRepository;
import com.backend.rpgtask.service.DailyService;
import com.backend.rpgtask.shared.dto.DailyDto;
import com.backend.rpgtask.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyServiceImpl implements DailyService {

    @Autowired
    DailyRepository dailyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public DailyDto createTask(DailyDto dailyDto) {

        DailyEntity dailyEntity = modelMapper.map(dailyDto, DailyEntity.class);
        dailyEntity.setUserId(userRepository.findByUserId(dailyDto.getUserId()));

        DailyEntity storedEntity = dailyRepository.save(dailyEntity);

        return modelMapper.map(storedEntity, DailyDto.class);
    }

    @Override
    public List<DailyDto> getTasks(UserDto userDto) {

        UserEntity userEntity = userRepository.findByUserId(userDto.getUserId());
        List<DailyEntity> dailyEntities = dailyRepository.findByUserIdOrderByOrderNumber(userEntity);

        List<DailyDto> response = new ArrayList<>();

        for (int i = 0; i < dailyEntities.size(); ++i) {
            response.add(modelMapper.map(dailyEntities.get(i), DailyDto.class));
        }

        return response;
    }

    @Override
    public void updateTask(DailyDto dailyDto, UserDto userDto) {
        DailyEntity dailyEntity = dailyRepository.findById(dailyDto.getId()).get();

        dailyEntity.setOrderNumber(dailyDto.getOrderNumber());
        dailyEntity.setTaskTitle(dailyDto.getTaskTitle());
        dailyEntity.setTaskText(dailyDto.getTaskText());

        dailyRepository.save(dailyEntity);
    }

    @Override
    public void deleteTask(Long id, UserDto userDto) {

        DailyEntity dailyEntity = dailyRepository.findById(id).get();

        dailyRepository.delete(dailyEntity);
    }
}
