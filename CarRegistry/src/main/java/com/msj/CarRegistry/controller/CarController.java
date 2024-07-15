package com.msj.CarRegistry.controller;

import com.msj.CarRegistry.controller.dtos.CarRequest;
import com.msj.CarRegistry.controller.dtos.CarResponse;
import com.msj.CarRegistry.controller.mapper.CarMapper;
import com.msj.CarRegistry.domain.Car;
import com.msj.CarRegistry.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cars")
@Slf4j
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarMapper carMapper;


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> getCar(@PathVariable Integer id){
        log.info("Retriving Car info");
        try{
            return ResponseEntity.ok(carService.getCarById(id));
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/findAll")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<?> getAllCars(){
        log.info("Retriving Car info");
        try{
            List<Car> cars = carService.getAllCars();
            List<CarResponse> response = new ArrayList<>();
            cars.forEach(car->{
                response.add(carMapper.toResponse(car));
            });
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> deleteCarById(@PathVariable Integer id){
        try{
            carService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> addCar(@RequestBody CarRequest carRequest){
        try{
            CarResponse response = carMapper.toResponse(carService.saveCar(carMapper.toModel(carRequest)));
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> updateCarById(@PathVariable Integer id,@RequestBody CarRequest carRequest){
        try{
            carService.updateById(id,carMapper.toModel(carRequest));
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
