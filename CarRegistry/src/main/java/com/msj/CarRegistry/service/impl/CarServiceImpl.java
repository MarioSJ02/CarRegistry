package com.msj.CarRegistry.service.impl;

import com.msj.CarRegistry.controller.dtos.CarRequest;
import com.msj.CarRegistry.controller.dtos.CarResponse;
import com.msj.CarRegistry.domain.Car;
import com.msj.CarRegistry.entity.CarEntity;
import com.msj.CarRegistry.repository.CarRepository;
import com.msj.CarRegistry.service.CarService;
import com.msj.CarRegistry.service.converters.CarConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarConverter carConverter;

    @Override
    public Car getCarById(Integer id) {
       Optional <CarEntity> carOptional = carRepository.findById(id);
       if(carOptional.isPresent()){
           return carConverter.toCar(carOptional.get());
       }
       return null;
    }


    @Override
    public void deleteById(Integer id) {
        log.info("Deleting car with id {}",id);
        carRepository.deleteById(id);

    }

    @Override
    public Car updateById(Integer id, Car car)  {
        log.info("Updateing car with id {}",id);
        Optional<CarEntity> carOptional = carRepository.findById(id);
        if(carOptional.isPresent()){
            CarEntity entity = carConverter.toEntity(car);
            entity.setId(id);

            return carConverter.toCar(carRepository.save(entity));
        }
        return null;
    }

    @Override
    public Car saveCar(Car car) {
        log.info("Adding car to database...");
        CarEntity entity = carConverter.toEntity(car);

        return carConverter.toCar(carRepository.save(entity));
    }

    @Override
    public List<Car> getAllCars(){
        List<CarEntity> carsList = carRepository.findAll();

        List<Car> cars = new ArrayList<>();
        carsList.forEach(car ->{
            cars.add(carConverter.toCar(car));
        });
        return cars;
    }
}
