package com.backend.rpgtask.io.repositories;

import com.backend.rpgtask.io.entity.HabitsEntity;
import com.backend.rpgtask.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitsRepository extends CrudRepository<HabitsEntity, Long> {
    List<HabitsEntity> findByUserIdOrderByOrderNumber(UserEntity userEntity);
}
