package com.example.entitymappingservice.service;

import com.example.entitymappingservice.dto.BuyerSellerMappingRequest;
import com.example.entitymappingservice.dto.BuyerSellerMappingResponse;
import com.example.entitymappingservice.dto.PendingMappingApprovalResponse;
import com.example.entitymappingservice.dto.RejectMappingRequest;

import java.util.List;

public interface BuyerSellerMappingService {

    BuyerSellerMappingResponse raiseMappingRequest(BuyerSellerMappingRequest buyerSellerMappingRequest);
    List<PendingMappingApprovalResponse> getPendingApprovals(Long sellerEntityId);
    BuyerSellerMappingResponse approveMappingRequest(Long requestId, Long sellerEntityId) ;
    BuyerSellerMappingResponse rejectMappingRequest(Long requestId, Long sellerEntityId, RejectMappingRequest rejectMappingRequest) ;

}
