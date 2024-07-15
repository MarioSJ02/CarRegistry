package com.msj.CarRegistry.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private int id;

    private Brand brand;

    private String model;

    private int milleage;

    private double price;

    private int year;

    private String description;

    private String colour;

    private String fuelType;

    private int numDoors;
}
