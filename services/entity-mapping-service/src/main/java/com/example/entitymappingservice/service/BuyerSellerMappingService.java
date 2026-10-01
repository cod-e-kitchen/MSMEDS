package com.example.entitymappingservice.service;

import com.example.entitymappingservice.dto.BuyerSellerMappingRequest;
import com.example.entitymappingservice.dto.BuyerSellerMappingResponse;

public interface BuyerSellerMappingService {

    BuyerSellerMappingResponse raiseMappingRequest(BuyerSellerMappingRequest buyerSellerMappingRequest);
}
