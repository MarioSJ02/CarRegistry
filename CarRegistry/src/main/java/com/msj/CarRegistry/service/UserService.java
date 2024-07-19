package com.msj.CarRegistry.service;

import com.msj.CarRegistry.domain.Image;
import com.msj.CarRegistry.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    public UserEntity save(UserEntity entity);

    public Image addUserImage(Long id, MultipartFile file)throws IOException;

    public byte[]getUserImage(Long id);

}
