package com.backend.rpgtask.io.repositories;

import com.backend.rpgtask.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
    UserEntity findByConfirmationToken(String confirmationToken);
}
