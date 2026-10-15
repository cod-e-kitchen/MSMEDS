package com.example.entitymappingservice.service;

import com.example.entitymappingservice.dto.BuyerSellerMappingRequest;
import com.example.entitymappingservice.dto.BuyerSellerMappingResponse;
import com.example.entitymappingservice.dto.PendingMappingApprovalResponse;
import com.example.entitymappingservice.dto.RejectMappingRequest;

import java.util.List;

public interface BuyerSellerMappingService {

    BuyerSellerMappingResponse raiseMappingRequest(BuyerSellerMappingRequest buyerSellerMappingRequest);
    List<PendingMappingApprovalResponse> getPendingApprovals();
    BuyerSellerMappingResponse approveMappingRequest(Long requestId) ;
    BuyerSellerMappingResponse rejectMappingRequest(Long requestId, RejectMappingRequest rejectMappingRequest) ;

}
