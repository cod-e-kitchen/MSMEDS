package com.example.entitymappingservice.controller;

import com.example.entitymappingservice.dto.BuyerSellerMappingRequest;
import com.example.entitymappingservice.dto.BuyerSellerMappingResponse;
import com.example.entitymappingservice.service.BuyerSellerMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mappings")
@RequiredArgsConstructor
public class BuyerSellerMappingController {

    private final BuyerSellerMappingService buyerSellerMappingService;

    @PostMapping("/raise-request")
    public ResponseEntity<BuyerSellerMappingResponse> createMapping
            (@RequestBody BuyerSellerMappingRequest buyerSellerMappingRequest){
        BuyerSellerMappingResponse buyerSellerMappingResponse = new BuyerSellerMappingResponse();
        buyerSellerMappingResponse = buyerSellerMappingService.raiseMappingRequest(buyerSellerMappingRequest);

        return new ResponseEntity<>(buyerSellerMappingResponse, HttpStatus.OK);

    }

}
