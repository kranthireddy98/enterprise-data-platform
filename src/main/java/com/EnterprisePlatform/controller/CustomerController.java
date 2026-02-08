package com.EnterprisePlatform.controller;

import com.EnterprisePlatform.DTO.CustomerCreateRequest;
import com.EnterprisePlatform.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<Map<String,Object>> fetchAllCustomers(){

        return service.fetchAllCustomers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){

        boolean deleted = service.deleteCustomer(id);

        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerCreateRequest request){
        service.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
