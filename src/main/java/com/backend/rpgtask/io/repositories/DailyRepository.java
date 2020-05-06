package com.backend.rpgtask.io.repositories;

import com.backend.rpgtask.io.entity.DailyEntity;
import com.backend.rpgtask.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyRepository extends CrudRepository<DailyEntity, Long> {
    List<DailyEntity> findByUserIdOrderByOrderNumber(UserEntity userEntity);
}
