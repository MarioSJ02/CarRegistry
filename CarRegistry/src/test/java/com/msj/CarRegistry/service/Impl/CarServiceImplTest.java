package com.msj.CarRegistry.service.Impl;

import com.msj.CarRegistry.controller.dtos.BrandRequest;

import com.msj.CarRegistry.domain.Brand;
import com.msj.CarRegistry.domain.Car;
import com.msj.CarRegistry.entity.CarEntity;
import com.msj.CarRegistry.repository.CarRepository;
import com.msj.CarRegistry.service.converters.CarConverter;
import com.msj.CarRegistry.service.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarConverter carConverter;

    private Car car;
    private CarEntity carEntity;

    @BeforeEach
    void setUp() {
        Brand brand = new Brand(1, "Toyota", 5, "Japan");
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setId(1);
        brandRequest.setName("Toyota");
        brandRequest.setWarranty(5);
        brandRequest.setCountry("Japan");

        car = new Car(1, brand, "Corolla", 20000, 15000.0, 2019, "Good condition", "Red", "Gasoline", 4);
        carEntity = new CarEntity(1, null, "Corolla", 20000, 15000.0, 2019, "Good condition", "Red", "Gasoline", 4);
    }

    @Test
    void testGetCarById() {
        when(carRepository.findById(1)).thenReturn(Optional.of(carEntity));
        when(carConverter.toCar(any(CarEntity.class))).thenReturn(car);

        Car result = carService.getCarById(1);

        assertEquals(1, result.getId());
        assertEquals("Toyota", result.getBrand().getName());
        assertEquals("Corolla", result.getModel());
        assertEquals(2019, result.getYear());
        verify(carRepository, times(1)).findById(1);
        verify(carConverter, times(1)).toCar(any(CarEntity.class));
    }

    @Test
    void testGetAllCars() {
        List<CarEntity> carEntities = new ArrayList<>();
        carEntities.add(carEntity);

        when(carRepository.findAll()).thenReturn(carEntities);
        when(carConverter.toCar(any(CarEntity.class))).thenReturn(car);

        List<Car> result = carService.getAllCars();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Toyota", result.get(0).getBrand().getName());
        assertEquals("Corolla", result.get(0).getModel());
        assertEquals(2019, result.get(0).getYear());
        verify(carRepository, times(1)).findAll();
        verify(carConverter, times(1)).toCar(any(CarEntity.class));
    }

    @Test
    void testSaveCar() {
        when(carConverter.toEntity(any(Car.class))).thenReturn(carEntity);
        when(carRepository.save(any(CarEntity.class))).thenReturn(carEntity);
        when(carConverter.toCar(any(CarEntity.class))).thenReturn(car);

        Car result = carService.saveCar(car);

        assertEquals(1, result.getId());
        assertEquals("Toyota", result.getBrand().getName());
        assertEquals("Corolla", result.getModel());
        assertEquals(2019, result.getYear());
        verify(carConverter, times(1)).toEntity(any(Car.class));
        verify(carRepository, times(1)).save(any(CarEntity.class));
        verify(carConverter, times(1)).toCar(any(CarEntity.class));
    }

    @Test
    void testUpdateCarById() {
        when(carRepository.findById(1)).thenReturn(Optional.of(carEntity));
        when(carConverter.toEntity(any(Car.class))).thenReturn(carEntity);
        when(carRepository.save(any(CarEntity.class))).thenReturn(carEntity);
        when(carConverter.toCar(any(CarEntity.class))).thenReturn(car);

        Car updatedCar = new Car(1, new Brand(1, "Toyota", 5, "Japan"), "Updated Corolla", 25000, 18000.0, 2020, "Updated description", "Blue", "Gasoline", 4);
        Car result = carService.updateById(1, updatedCar);

        assertEquals(1, result.getId());
        assertEquals("Toyota", result.getBrand().getName());
        assertEquals("Updated Corolla", result.getModel());
        assertEquals(2020, result.getYear());
        assertEquals("Updated description", result.getDescription());
        assertEquals("Blue", result.getColour());
        verify(carRepository, times(1)).findById(1);
        verify(carConverter, times(1)).toEntity(any(Car.class));
        verify(carRepository, times(1)).save(any(CarEntity.class));
        verify(carConverter, times(1)).toCar(any(CarEntity.class));
    }

    @Test
    void testDeleteCarById() {
        doNothing().when(carRepository).deleteById(1);

        carService.deleteById(1);

        verify(carRepository, times(1)).deleteById(1);
    }
}
