package com.msj.CarRegistry.service.converters;

import com.msj.CarRegistry.domain.User;
import com.msj.CarRegistry.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    /**
     * Convierte una entidad UserEntity a un objeto de dominio User.
     *
     * @param entity La entidad UserEntity
     * @return El objeto de dominio User
     */
    public User toUser(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setMail(entity.getMail());
        user.setPassword(entity.getPassword());
        user.setRole(entity.getRole());
        return user;
    }

    /**
     * Convierte un objeto de dominio User a una entidad UserEntity.
     *
     * @param user El objeto de dominio User
     * @return La entidad UserEntity
     */
    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setMail(user.getMail());
        entity.setPassword(user.getPassword());
        entity.setRole(user.getRole());
        return entity;
    }
}
