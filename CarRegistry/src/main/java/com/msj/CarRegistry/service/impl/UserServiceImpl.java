package com.msj.CarRegistry.service.impl;

import com.msj.CarRegistry.domain.Image;
import com.msj.CarRegistry.entity.ImageEntity;
import com.msj.CarRegistry.entity.UserEntity;
import com.msj.CarRegistry.repository.ImageRepository;
import com.msj.CarRegistry.repository.UserRepository;
import com.msj.CarRegistry.service.UserService;
import com.msj.CarRegistry.service.converters.ImageConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ImageRepository imageRepository;

    @Autowired
    private ImageConverter imageConverter;




    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByMail(username)
                        .orElseThrow(() -> new  UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public UserEntity save(UserEntity entity) {
        return userRepository.save(entity);
    }

    @Override
    public Image addUserImage(Long id, MultipartFile file) throws IOException {
        UserEntity entity = userRepository.findById(id).orElseThrow(RuntimeException::new);
        log.info("Saving user image...");
        //Buscar si ya existe una imagen asociada al usuario
        ImageEntity image = imageRepository.findByUserId(id);
        if(image == null){
            //Crear una nueva imagen si no existe
            image = new ImageEntity();
            image.setUser(entity);
        }
        //Convertir archivo a Base64
        image.setData(Base64.getEncoder().encodeToString(file.getBytes()));
        return imageConverter.toImage(imageRepository.save(image));
    }

    @Override
    public byte[] getUserImage(Long id) {
        ImageEntity image = imageRepository.findByUserId(id);
        if(image!= null){
            return Base64.getDecoder().decode(image.getData());
        }
        return new byte[0];

    }
}
