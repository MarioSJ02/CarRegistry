package com.msj.CarRegistry.repository;

import com.msj.CarRegistry.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    ImageEntity findByUserId(Long id);
}
