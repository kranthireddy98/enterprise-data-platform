package com.enterprise.platform.service;

import com.enterprise.platform.repository.query.CustomerQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private final CustomerQueryRepository queryRepository;

    public CustomerService(CustomerQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    public List<Map<String,Object>> fetchAllCustomers() {

        return queryRepository.fetchAll();
    }
}
