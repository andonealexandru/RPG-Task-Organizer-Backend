package com.backend.rpgtask.io.repositories;

import com.backend.rpgtask.io.entity.RoleEntity;
import com.backend.rpgtask.io.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleName roleName);
}
