package com.msj.CarRegistry.controller;

import com.msj.CarRegistry.controller.dtos.CarRequest;
import com.msj.CarRegistry.domain.Car;
import com.msj.CarRegistry.entity.CarEntity;
import com.msj.CarRegistry.service.CarService;
import com.msj.CarRegistry.controller.dtos.CarResponse;
import com.msj.CarRegistry.controller.mapper.CarMapper;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public ResponseEntity<?> getCar(@PathVariable Long id){
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
    public ResponseEntity<?> deleteCarById(@PathVariable Long id){
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
    public ResponseEntity<?> updateCarById(@PathVariable Long id,@RequestBody CarRequest carRequest){
        try{
            carService.updateById(id,carMapper.toModel(carRequest));
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para descargar los datos de los coches en formato CSV.
     *
     * @return Datos de los coches en formato CSV
     */
    @GetMapping("/downloadCsv")
    @PreAuthorize("hasAnyRole('CLIENT', 'VENDOR')")
    public ResponseEntity<byte[]> downloadCarsCsv() {
        try {
            List<CarEntity> carEntities = carService.getAllCarEntities();
            ByteArrayInputStream in = carService.generateCsv(carEntities);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "cars.csv");

            return new ResponseEntity<>(in.readAllBytes(), headers, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Error generating CSV", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para añadir coches desde un archivo CSV.
     *
     * @param file Archivo CSV con datos de coches
     * @return Mensaje de éxito o error
     */
    @PostMapping("/uploadCsv")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<String> uploadCarsCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        try {
            InputStream inputStream = file.getInputStream();
            List<CarEntity> carEntities = carService.parseCsv(inputStream);
            carService.saveAll(carEntities);

            return ResponseEntity.ok("Cars uploaded successfully");
        } catch (IOException | CsvException e) {
            log.error("Error processing CSV file", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to process CSV file");
        }
    }


}
