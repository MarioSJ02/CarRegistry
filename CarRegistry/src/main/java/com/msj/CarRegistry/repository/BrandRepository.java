package com.msj.CarRegistry.repository;

import com.msj.CarRegistry.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {
    BrandEntity findByName(String name);
}
