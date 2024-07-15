package com.msj.CarRegistry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Car")
public class CarEntity {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brand;

    private String model;

    private int milleage;

    private double price;

    private int year;

    private String description;

    private String colour;

    private String fuelType;

    private int numDoors;
}
