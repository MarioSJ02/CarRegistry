package com.msj.CarRegistry.repository;

import com.msj.CarRegistry.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByMail(String email);
}
