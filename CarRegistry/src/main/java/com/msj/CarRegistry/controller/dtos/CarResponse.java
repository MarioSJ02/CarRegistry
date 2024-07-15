package com.msj.CarRegistry.controller.dtos;

import lombok.Data;

@Data
public class CarResponse {

    private Integer id;

    private BrandResponse brand;

    private String model;

    private Integer milleage;

    private Double price;

    private Integer year;

    private String description;

    private String colour;

    private String fuelType;

    private Integer numDoors;
}
