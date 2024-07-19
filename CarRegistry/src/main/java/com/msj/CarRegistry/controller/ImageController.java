package com.msj.CarRegistry.controller;

import com.msj.CarRegistry.domain.Image;
import com.msj.CarRegistry.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@Slf4j
public class ImageController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint para añadir o actualizar la imagen para un usuario existente.
     *
     * @param userId ID del usuario
     * @param file   Imagen en formato MultipartFile
     * @return Mensaje de éxito o error
     */
    @PostMapping("/{userId}/upload")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<String> uploadImage(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {
            Image image = userService.addUserImage(userId, file);
            if (image != null) {
                return ResponseEntity.ok("Image uploaded successfully with ID: " + image.getId());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (IOException e) {
            log.error("Error uploading image", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Endpoint para descargar la imagen de un usuario por ID.
     *
     * @param userId ID del usuario
     * @return Imagen en formato byte array
     */
    @GetMapping("/{userId}/download")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long userId) {
        byte[] imageData = userService.getUserImage(userId);
        if (imageData != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
