package com.msj.CarRegistry.repository;

import com.msj.CarRegistry.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<CarEntity,Integer> {

}
