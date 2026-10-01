package com.example.entitycreateservice.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class EntityResponseDto {

    private BigInteger entity_id;
    private String status;
    private String remarks;
}
