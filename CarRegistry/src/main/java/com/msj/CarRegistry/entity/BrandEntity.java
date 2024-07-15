package com.msj.CarRegistry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Brand")
public class BrandEntity {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    private int warranty;

    private String country;

    @OneToMany(mappedBy = "brand")
    List<CarEntity> car;
}
