package com.example.entitycreateservice.controller;

import com.example.entitycreateservice.dto.EntityRequestDto;
import com.example.entitycreateservice.dto.EntityResponseDto;
import com.example.entitycreateservice.service.EntityCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/entity")
public class EntityCreateController {

    private final EntityCreateService entityCreateService;

    @PostMapping("/create")
    public ResponseEntity<?> createEntity(@RequestBody EntityRequestDto entityRequestDto){

    EntityResponseDto entityResponseDto = entityCreateService.createEntityService(entityRequestDto);

    return ResponseEntity.ok(entityResponseDto);

    }
}
