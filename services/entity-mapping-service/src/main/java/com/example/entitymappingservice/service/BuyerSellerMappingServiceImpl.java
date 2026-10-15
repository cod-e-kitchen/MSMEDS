package com.example.entitymappingservice.service;

import com.example.entitymappingservice.dto.BuyerSellerMappingRequest;
import com.example.entitymappingservice.dto.BuyerSellerMappingResponse;
import com.example.entitymappingservice.dto.PendingMappingApprovalResponse;
import com.example.entitymappingservice.dto.RejectMappingRequest;
import com.example.entitymappingservice.exception.InvalidEntityException;
import com.example.entitymappingservice.repository.BuyerSellerMappingRepository;
import com.example.msmedscommonutil.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.msmedscommonutil.security.CurrentUser;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyerSellerMappingServiceImpl implements BuyerSellerMappingService {

    private final BuyerSellerMappingRepository buyerSellerMappingRepository;
    private final CurrentUser currentUser;

    @Override
    public BuyerSellerMappingResponse raiseMappingRequest
            (BuyerSellerMappingRequest buyerSellerMappingRequest) {

        AuthenticatedUser user =currentUser.get();

        if(!"BUYER".equals(user.entityType())){
            throw new InvalidEntityException("Invalid entity type for mapping request");
        }
        return buyerSellerMappingRepository.raiseMappingRequest(
                buyerSellerMappingRequest,
                user
        );



//        return buyerSellerMappingRepository.raiseMappingRequest(buyerSellerMappingRequest);
    }

    @Override
    public List<PendingMappingApprovalResponse> getPendingApprovals() {

        AuthenticatedUser user = currentUser.get();
        if(!"SELLER".equals(user.entityType())){
            throw new InvalidEntityException("Invalid entity type for request");
        }

        return buyerSellerMappingRepository.getPendingApprovals(user);

//        return buyerSellerMappingRepository.getPendingApprovals(sellerEntityId);
    }

    @Override
    public BuyerSellerMappingResponse approveMappingRequest(Long requestId) {

        AuthenticatedUser user = currentUser.get();
        if(!"SELLER".equals(user.entityType())){
            throw new InvalidEntityException("Invalid entity type for request");
        }

        return buyerSellerMappingRepository.approveMappingRequest(requestId,user);

//        return buyerSellerMappingRepository.approveMappingRequest(requestId, sellerEntityId);
    }

    @Override
    public BuyerSellerMappingResponse rejectMappingRequest(Long requestId,
                                                           RejectMappingRequest rejectMappingRequest) {

        AuthenticatedUser user = currentUser.get();
        if(!"SELLER".equals(user.entityType())){
            throw new InvalidEntityException("Invalid entity type for request");
        }

        return buyerSellerMappingRepository.rejectMappingRequest(requestId, user, rejectMappingRequest);
    }


}
