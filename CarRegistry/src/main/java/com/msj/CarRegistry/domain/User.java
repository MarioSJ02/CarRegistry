package com.msj.CarRegistry.domain;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User{
    Long id;

    String name;


    String mail;

    String password;

    String role;

    private Image image;
}
