package com.msj.CarRegistry.service.converters;

import com.msj.CarRegistry.domain.Brand;
import com.msj.CarRegistry.entity.BrandEntity;
import org.springframework.stereotype.Component;

@Component
public class BrandConverter {
    public Brand toBrand(BrandEntity entity){
        Brand brand = new Brand();

        brand.setId(entity.getId());
        brand.setName(entity.getName());
        brand.setWarranty(entity.getWarranty());
        brand.setCountry(entity.getCountry());

        return brand;
    }

    public BrandEntity toEntity(Brand brand){
        BrandEntity entity = new BrandEntity();

        entity.setId(brand.getId());
        entity.setName(brand.getName());
        entity.setWarranty(brand.getWarranty());
        entity.setCountry(brand.getCountry());

        return entity;
    }
}
