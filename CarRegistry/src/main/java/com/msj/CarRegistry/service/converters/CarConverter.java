package com.msj.CarRegistry.service.converters;

import com.msj.CarRegistry.domain.Car;
import com.msj.CarRegistry.entity.CarEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarConverter {

    @Autowired
    private BrandConverter brandConverter;

    public Car toCar(CarEntity entity){
        Car car = new Car();
        car.setId(entity.getId());
        car.setBrand(brandConverter.toBrand(entity.getBrand()));
        car.setModel(entity.getModel());
        car.setYear(entity.getYear());
        car.setColour(entity.getColour());
        car.setMilleage(entity.getMilleage());
        car.setDescription(entity.getDescription());

        return car;
    }

    public CarEntity toEntity(Car car){
        CarEntity entity = new CarEntity();
        entity.setId(car.getId());
        entity.setBrand(brandConverter.toEntity(car.getBrand()));
        entity.setModel(car.getModel());
        entity.setYear(car.getYear());
        entity.setColour(car.getColour());
        entity.setMilleage(car.getMilleage());
        entity.setDescription(car.getDescription());

        return entity;
    }
}
