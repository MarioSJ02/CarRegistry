package com.msj.CarRegistry.controller.mapper;



import com.msj.CarRegistry.controller.dtos.BrandRequest;
import com.msj.CarRegistry.controller.dtos.BrandResponse;
import com.msj.CarRegistry.domain.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public BrandResponse toResponse(Brand entity){
        BrandResponse brand = new BrandResponse();

        brand.setId(entity.getId());
        brand.setName(entity.getName());
        brand.setWarranty(entity.getWarranty());
        brand.setCountry(entity.getCountry());

        return brand;
    }

    public Brand toModel(BrandRequest response){
        Brand brand = new Brand();

        brand.setId(response.getId());
        brand.setName(response.getName());
        brand.setWarranty(response.getWarranty());
        brand.setCountry(response.getCountry());

        return brand;
    }
}
