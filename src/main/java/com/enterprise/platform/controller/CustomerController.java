package com.enterprise.platform.controller;

import com.enterprise.platform.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
