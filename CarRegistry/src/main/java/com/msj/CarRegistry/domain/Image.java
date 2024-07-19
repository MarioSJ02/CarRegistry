package com.msj.CarRegistry.domain;

import com.msj.CarRegistry.entity.UserEntity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private Long id;
    private String data;
    private User user;
}
