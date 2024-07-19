package com.msj.CarRegistry.service;

import com.msj.CarRegistry.domain.Brand;

public interface BrandService {
    public Brand findByName(String name);
}
