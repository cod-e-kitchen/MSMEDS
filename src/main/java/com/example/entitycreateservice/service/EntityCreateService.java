package com.example.entitycreateservice.service;

import com.example.entitycreateservice.dto.EntityRequestDto;
import com.example.entitycreateservice.dto.EntityResponseDto;
import com.example.entitycreateservice.repository.EntityCreateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityCreateService implements EntityCreateServiceIntf {

    private final EntityCreateRepository entityCreateRepository;

    @Override
    public EntityResponseDto createEntityService(EntityRequestDto entityRequestDto){
        EntityResponseDto entityResponseDto = entityCreateRepository.addEntityToDs(entityRequestDto);
        entityResponseDto.setRemarks("Entity has been onboarded successfully");
        return entityResponseDto;
    }
}
