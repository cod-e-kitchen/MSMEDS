package com.example.entitycreateservice.dto;

import lombok.Data;

@Data
public class EntityRequestDto {

    private String entity_name;
    private String entity_location_city;
    private String entity_location_state;
    private String entity_location_country;
    private String entity_type;

    private String username;
    private String password;
}
