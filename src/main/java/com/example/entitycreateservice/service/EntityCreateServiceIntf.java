package com.example.entitycreateservice.service;

import com.example.entitycreateservice.dto.EntityRequestDto;
import com.example.entitycreateservice.dto.EntityResponseDto;

public interface EntityCreateServiceIntf {

    EntityResponseDto createEntityService(EntityRequestDto entityRequestDto);
}
