package com.msj.CarRegistry.service.converters;

import com.msj.CarRegistry.entity.ImageEntity;
import com.msj.CarRegistry.domain.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {
    @Autowired
    UserConverter userConverter;

    public Image toImage(ImageEntity entity){
        return new Image(entity.getId(), entity.getData(), userConverter.toUser(entity.getUser()));
    }

    public ImageEntity toEntity(Image image){
        return new ImageEntity(image.getId(), image.getData(), userConverter.toEntity(image.getUser()));
    }
}
