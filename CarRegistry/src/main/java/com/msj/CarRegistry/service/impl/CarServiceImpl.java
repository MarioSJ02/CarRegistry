package com.msj.CarRegistry.service.impl;

import com.msj.CarRegistry.entity.BrandEntity;
import com.msj.CarRegistry.repository.BrandRepository;
import com.msj.CarRegistry.service.converters.BrandConverter;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.CSVReader;
import com.msj.CarRegistry.domain.Car;
import com.msj.CarRegistry.entity.CarEntity;
import com.msj.CarRegistry.repository.CarRepository;
import com.msj.CarRegistry.service.CarService;
import com.msj.CarRegistry.service.converters.CarConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CarConverter carConverter;

    @Autowired
    private BrandConverter brandConverter;

    @Override
    public Car getCarById(Long id) {
       Optional <CarEntity> carOptional = carRepository.findById(id);
       if(carOptional.isPresent()){
           return carConverter.toCar(carOptional.get());
       }
       return null;
    }


    @Override
    public void deleteById(Long id) {
        log.info("Deleting car with id {}",id);
        carRepository.deleteById(id);

    }

    @Override
    public Car updateById(Long id, Car car)  {
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
    @Override
    public List<CarEntity> parseCsv(InputStream inputStream) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            List<String[]> records = reader.readAll();
            List<CarEntity> carEntities = new ArrayList<>();
            for (String[] record : records) {
                if (record.length < 10 || "ID".equals(record[0])) continue;

                CarEntity carEntity = new CarEntity();
                carEntity.setId(Long.parseLong(record[0]));
                BrandEntity brandEntity = brandRepository.findByName(record[1]);
                carEntity.setBrand(brandEntity);
                carEntity.setModel(record[2]);
                carEntity.setMilleage(Integer.parseInt(record[3]));
                carEntity.setPrice(Double.parseDouble(record[4]));
                carEntity.setYear(Integer.parseInt(record[5]));
                carEntity.setDescription(record[6]);
                carEntity.setColour(record[7]);
                carEntity.setFuelType(record[8]);
                carEntity.setNumDoors(Integer.parseInt(record[9]));
                carEntities.add(carEntity);
            }
            return carEntities;
        }
    }
    @Override
    public ByteArrayInputStream generateCsv(List<CarEntity> carEntities) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(out))) {
            writer.writeNext(new String[]{
                    "ID", "BrandName", "Model", "Mileage", "Price", "Year", "Description", "Colour", "FuelType", "NumDoors"
            });
            for (CarEntity carEntity : carEntities) {
                writer.writeNext(new String[]{
                        Long.toString(carEntity.getId()),
                        carEntity.getBrand() != null ? carEntity.getBrand().getName() : "",
                        carEntity.getModel(),
                        Integer.toString(carEntity.getMilleage()),
                        Double.toString(carEntity.getPrice()),
                        Integer.toString(carEntity.getYear()),
                        carEntity.getDescription() != null ? carEntity.getDescription() : "",
                        carEntity.getColour() != null ? carEntity.getColour() : "",
                        carEntity.getFuelType() != null ? carEntity.getFuelType() : "",
                        Integer.toString(carEntity.getNumDoors())
                });
            }
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public List<CarEntity> getAllCarEntities() {
        return carRepository.findAll();
    }

    @Override
    public void saveAll(List<CarEntity> carEntities) {
        log.info("Adding all cars to database...");
        carEntities.forEach(car-> saveCar(carConverter.toCar(car)));
    }
}
