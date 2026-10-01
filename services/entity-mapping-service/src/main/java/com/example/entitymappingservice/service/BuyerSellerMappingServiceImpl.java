package com.example.entitymappingservice.service;

import com.example.entitymappingservice.dto.BuyerSellerMappingRequest;
import com.example.entitymappingservice.dto.BuyerSellerMappingResponse;
import com.example.entitymappingservice.repository.BuyerSellerMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyerSellerMappingServiceImpl implements BuyerSellerMappingService {

    private final BuyerSellerMappingRepository buyerSellerMappingRepository;

    @Override
    public BuyerSellerMappingResponse raiseMappingRequest(BuyerSellerMappingRequest buyerSellerMappingRequest) {
        return buyerSellerMappingRepository.raiseMappingRequest(buyerSellerMappingRequest);
    }
}
