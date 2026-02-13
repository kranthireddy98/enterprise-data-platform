package com.EnterprisePlatform.controller;


import com.EnterprisePlatform.DTO.BulkUploadRequest;
import com.EnterprisePlatform.DTO.BulkUploadResponse;
import com.EnterprisePlatform.service.BulkProcessingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bulk/customers")
public class BulkCustomerController {

    private final BulkProcessingService service;

    public BulkCustomerController(BulkProcessingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BulkUploadResponse> upload(
            @RequestBody BulkUploadRequest request
            ){

        BulkUploadResponse response=  service.process(request);

        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(response);
    }
}
