package com.msj.CarRegistry.controller;

import com.msj.CarRegistry.controller.dtos.BrandRequest;
import com.msj.CarRegistry.controller.dtos.BrandResponse;
import com.msj.CarRegistry.controller.dtos.CarRequest;
import com.msj.CarRegistry.controller.dtos.CarResponse;
import com.msj.CarRegistry.controller.mapper.CarMapper;
import com.msj.CarRegistry.domain.Brand;
import com.msj.CarRegistry.domain.Car;
import com.msj.CarRegistry.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private CarService carService;

    @Mock
    private CarMapper carMapper;

    private Car car;
    private CarRequest carRequest;
    private CarResponse carResponse;

    @BeforeEach
    void setUp() {
        Brand brand = new Brand(1, "Toyota", 5, "Japan");
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setId(1);
        brandRequest.setName("Toyota");
        brandRequest.setWarranty(5);
        brandRequest.setCountry("Japan");

        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setId(1);
        brandResponse.setName("Toyota");
        brandResponse.setWarranty(5);
        brandResponse.setCountry("Japan");

        car = new Car(1, brand, "Corolla", 20000, 15000.0, 2019, "Good condition", "Red", "Gasoline", 4);

        carRequest = new CarRequest();
        carRequest.setId(1);
        carRequest.setBrand(brandRequest);
        carRequest.setModel("Corolla");
        carRequest.setMilleage(20000);
        carRequest.setPrice(15000.0);
        carRequest.setYear(2019);
        carRequest.setDescription("Good condition");
        carRequest.setColour("Red");
        carRequest.setFuelType("Gasoline");
        carRequest.setNumDoors(4);

        carResponse = new CarResponse();
        carResponse.setId(1);
        carResponse.setBrand(brandResponse);
        carResponse.setModel("Corolla");
        carResponse.setMilleage(20000);
        carResponse.setPrice(15000.0);
        carResponse.setYear(2019);
        carResponse.setDescription("Good condition");
        carResponse.setColour("Red");
        carResponse.setFuelType("Gasoline");
        carResponse.setNumDoors(4);
    }

    @Test
    void testGetCar() {
        when(carService.getCarById(1)).thenReturn(car);

        ResponseEntity<?> response = carController.getCar(1);

        assertEquals(200, response.getStatusCodeValue());
        verify(carService, times(1)).getCarById(1);
    }

    @Test
    void testGetAllCars() {
        when(carService.getAllCars()).thenReturn(Arrays.asList(car));
        when(carMapper.toResponse(any(Car.class))).thenReturn(carResponse);

        ResponseEntity<?> response = carController.getAllCars();

        assertEquals(200, response.getStatusCodeValue());
        List<CarResponse> body = (List<CarResponse>) response.getBody();
        assertNotNull(body);
        assertEquals(1, body.size());
        verify(carService, times(1)).getAllCars();
        verify(carMapper, times(1)).toResponse(any(Car.class));
    }

    @Test
    void testDeleteCarById() {
        doNothing().when(carService).deleteById(1);

        ResponseEntity<?> response = carController.deleteCarById(1);

        assertEquals(200, response.getStatusCodeValue());
        verify(carService, times(1)).deleteById(1);
    }

    @Test
    void testAddCar() {
        when(carMapper.toModel(any(CarRequest.class))).thenReturn(car);
        when(carService.saveCar(any(Car.class))).thenReturn(car);
        when(carMapper.toResponse(any(Car.class))).thenReturn(carResponse);

        ResponseEntity<?> response = carController.addCar(carRequest);

        assertEquals(200, response.getStatusCodeValue());
        verify(carService, times(1)).saveCar(any(Car.class));
        verify(carMapper, times(1)).toModel(any(CarRequest.class));
        verify(carMapper, times(1)).toResponse(any(Car.class));
    }

    @Test
    void testUpdateCarById() {
        when(carMapper.toModel(any(CarRequest.class))).thenReturn(car);
        when(carService.updateById(anyInt(), any(Car.class))).thenReturn(car);

        ResponseEntity<?> response = carController.updateCarById(1, carRequest);

        assertEquals(200, response.getStatusCodeValue());
        verify(carService, times(1)).updateById(anyInt(), any(Car.class));
        verify(carMapper, times(1)).toModel(any(CarRequest.class));
    }
}
