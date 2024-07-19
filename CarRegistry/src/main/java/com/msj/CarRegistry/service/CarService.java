package com.msj.CarRegistry.service;

import com.msj.CarRegistry.domain.Car;
import com.msj.CarRegistry.entity.CarEntity;
import com.opencsv.exceptions.CsvException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public interface CarService {

    Car getCarById(Long id) ;

    void deleteById(Long id) ;

    Car updateById(Long id, Car car) ;

    Car saveCar(Car car) ;

    List<Car> getAllCars();

    public List<CarEntity> parseCsv(InputStream inputStream) throws IOException, CsvException;

    public ByteArrayInputStream generateCsv(List<CarEntity> carEntities) throws IOException;

    public List<CarEntity> getAllCarEntities();

    public void saveAll(List<CarEntity> carEntities);
}
