package com.example.entitymappingservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerSellerMappingRequest {

    @NotBlank
    private String sellerEntityId;
//    private String buyerEntityId;
}
