package com.msj.CarRegistry.service;

import com.msj.CarRegistry.domain.Car;
import java.util.List;


public interface CarService {

    Car getCarById(Integer id) ;

    void deleteById(Integer id) ;

    Car updateById(Integer id, Car car) ;

    Car saveCar(Car car) ;

    List<Car> getAllCars();
}
