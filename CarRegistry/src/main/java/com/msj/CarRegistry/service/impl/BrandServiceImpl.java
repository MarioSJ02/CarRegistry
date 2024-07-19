package com.msj.CarRegistry.service.impl;

import com.msj.CarRegistry.domain.Brand;
import com.msj.CarRegistry.entity.BrandEntity;
import com.msj.CarRegistry.repository.BrandRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BrandServiceImpl {
    @Autowired
    private BrandRepository brandRepository;

    public Brand findByName(String name) {
        BrandEntity brandEntity = brandRepository.findByName(name);
        if (brandEntity != null) {
            // Convertir BrandEntity a Brand
            return new Brand(
                    brandEntity.getId(),
                    brandEntity.getName(),
                    brandEntity.getWarranty(),
                    brandEntity.getCountry()
            );
        }
        return null;
    }
}
