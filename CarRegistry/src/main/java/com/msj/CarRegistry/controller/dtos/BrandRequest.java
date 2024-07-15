package com.msj.CarRegistry.controller.dtos;

import lombok.Data;

@Data
public class BrandRequest {
    private int id;

    private String name;

    private int warranty;

    private String country;
}
