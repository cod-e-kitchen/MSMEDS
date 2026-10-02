package com.example.entitymappingservice.service;

import com.example.entitymappingservice.dto.BuyerSellerMappingRequest;
import com.example.entitymappingservice.dto.BuyerSellerMappingResponse;
import com.example.entitymappingservice.dto.PendingMappingApprovalResponse;
import com.example.entitymappingservice.dto.RejectMappingRequest;
import com.example.entitymappingservice.repository.BuyerSellerMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyerSellerMappingServiceImpl implements BuyerSellerMappingService {

    private final BuyerSellerMappingRepository buyerSellerMappingRepository;

    @Override
    public BuyerSellerMappingResponse raiseMappingRequest(BuyerSellerMappingRequest buyerSellerMappingRequest) {
        return buyerSellerMappingRepository.raiseMappingRequest(buyerSellerMappingRequest);
    }

    @Override
    public List<PendingMappingApprovalResponse> getPendingApprovals(Long sellerEntityId) {
        return buyerSellerMappingRepository.getPendingApprovals(sellerEntityId);
    }

    @Override
    public BuyerSellerMappingResponse approveMappingRequest(Long requestId, Long sellerEntityId) {
        return buyerSellerMappingRepository.approveMappingRequest(requestId, sellerEntityId);
    }

    @Override
    public BuyerSellerMappingResponse rejectMappingRequest(Long requestId, Long sellerEntityId, RejectMappingRequest rejectMappingRequest) {
        return buyerSellerMappingRepository.rejectMappingRequest(requestId, sellerEntityId, rejectMappingRequest);
    }


}
