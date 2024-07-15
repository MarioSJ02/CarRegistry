package com.msj.CarRegistry.service.impl;

import com.msj.CarRegistry.entity.UserEntity;
import com.msj.CarRegistry.repository.UserRepository;
import com.msj.CarRegistry.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


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
}
