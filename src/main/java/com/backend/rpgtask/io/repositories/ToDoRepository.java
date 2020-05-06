package com.backend.rpgtask.io.repositories;

import com.backend.rpgtask.io.entity.ToDoEntity;
import com.backend.rpgtask.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends CrudRepository<ToDoEntity, Long> {
    List<ToDoEntity> findByUserIdOrderByOrderNumber(UserEntity userEntity);
}
