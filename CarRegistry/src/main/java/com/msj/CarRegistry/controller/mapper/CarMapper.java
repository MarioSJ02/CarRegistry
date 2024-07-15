package com.msj.CarRegistry.controller.mapper;

import com.msj.CarRegistry.controller.dtos.CarRequest;
import com.msj.CarRegistry.controller.dtos.CarResponse;
import com.msj.CarRegistry.domain.Car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {
    @Autowired
    private BrandMapper brandMapper;

    public CarResponse toResponse(Car entity){
        CarResponse car = new CarResponse();
        car.setId(entity.getId());
        car.setBrand(brandMapper.toResponse(entity.getBrand()));
        car.setModel(entity.getModel());
        car.setYear(entity.getYear());
        car.setColour(entity.getColour());
        car.setMilleage(entity.getMilleage());
        car.setDescription(entity.getDescription());

        return car;
    }

    public Car toModel(CarRequest model){
        Car car = new Car();
        car.setId(model.getId());
        car.setBrand(brandMapper.toModel(model.getBrand()));
        car.setModel(model.getModel());
        car.setYear(model.getYear());
        car.setColour(model.getColour());
        car.setMilleage(model.getMilleage());
        car.setDescription(model.getDescription());

        return car;
    }
}
