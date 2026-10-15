package com.example.entitymappingservice.controller;

import com.example.entitymappingservice.dto.BuyerSellerMappingRequest;
import com.example.entitymappingservice.dto.BuyerSellerMappingResponse;
import com.example.entitymappingservice.dto.PendingMappingApprovalResponse;
import com.example.entitymappingservice.dto.RejectMappingRequest;
import com.example.entitymappingservice.service.BuyerSellerMappingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mappings")
@RequiredArgsConstructor
public class BuyerSellerMappingController {

    private final BuyerSellerMappingService buyerSellerMappingService;

    @PostMapping("/raise-request")
    public ResponseEntity<BuyerSellerMappingResponse> createMapping
            (@Valid @RequestBody BuyerSellerMappingRequest buyerSellerMappingRequest){
        BuyerSellerMappingResponse buyerSellerMappingResponse = new BuyerSellerMappingResponse();
        buyerSellerMappingResponse = buyerSellerMappingService.raiseMappingRequest(buyerSellerMappingRequest);

        return new ResponseEntity<>(buyerSellerMappingResponse, HttpStatus.OK);

    }

    @GetMapping("/pending-approvals")
    public ResponseEntity<List<PendingMappingApprovalResponse>> getPendingApprovals
            (){

        List<PendingMappingApprovalResponse> pendingMappingApprovalResponseList =
                buyerSellerMappingService.getPendingApprovals();

        return new ResponseEntity<>(pendingMappingApprovalResponseList, HttpStatus.OK);
    }

    @PostMapping("/{requestId}/approve")
    public ResponseEntity<BuyerSellerMappingResponse> approveMappingBySeller
            (@PathVariable Long requestId){

        BuyerSellerMappingResponse buyerSellerMappingResponse =
                buyerSellerMappingService.approveMappingRequest(requestId);

        return new ResponseEntity<>(buyerSellerMappingResponse,HttpStatus.OK);
    }

    @PostMapping("/{requestId}/reject")
    public ResponseEntity<?> rejectMappingBySeller
            (@PathVariable Long requestId,
             @Valid @RequestBody RejectMappingRequest rejectRequest){
        BuyerSellerMappingResponse buyerSellerMappingResponse =
                buyerSellerMappingService.rejectMappingRequest(requestId,rejectRequest);
        return new ResponseEntity<>(buyerSellerMappingResponse, HttpStatus.OK);
    }

    /*TEST ENDPOINT - REMOVE LATER*/
//    @GetMapping("/test")
//    public String test(){
//        return "mapping service working properly";
//    }

}
