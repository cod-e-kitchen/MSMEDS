package com.example.entitycreateservice.service;

import com.example.entitycreateservice.dto.EntityRequestDto;
import com.example.entitycreateservice.dto.EntityResponseDto;
import com.example.entitycreateservice.repository.EntityCreateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntityCreateService implements EntityCreateServiceIntf {

    private final EntityCreateRepository entityCreateRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public EntityResponseDto createEntityService(EntityRequestDto entityRequestDto){
        log.info("Inside EntityCreateService......");
        log.info("Password before hashing : " + entityRequestDto.getPassword());
        String password_hash = passwordEncoder.encode(entityRequestDto.getPassword());
        log.info("Password after hashing : " + password_hash);
        entityRequestDto.setPassword(password_hash);
        log.info("Calling repository");
        EntityResponseDto entityResponseDto = entityCreateRepository.addEntityToDs(entityRequestDto);
        entityResponseDto.setRemarks("Entity has been onboarded successfully");
        return entityResponseDto;
    }
}
