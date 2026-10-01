package com.example.entitymappingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuyerSellerMappingRequest {
    private String buyerEntityId;
    private String sellerEntityId;
}
