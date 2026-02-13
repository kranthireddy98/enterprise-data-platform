package com.EnterprisePlatform.service;

import com.EnterprisePlatform.DTO.CustomerCreateRequest;
import com.EnterprisePlatform.repository.command.CustomerCommandRepository;
import com.EnterprisePlatform.repository.query.CountryQueryRepository;
import com.EnterprisePlatform.repository.query.CustomerQueryRepository;
import com.EnterprisePlatform.transaction.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerQueryRepository queryRepository;
    private final CustomerCommandRepository  customerCommandRepository;
    private final TransactionManager transactionManager;
    private final CountryQueryRepository countryQueryRepository;

    public CustomerService(CustomerQueryRepository queryRepository,
                           CustomerCommandRepository customerCommandRepository,
                           TransactionManager transactionManager,
                           CountryQueryRepository countryQueryRepository) {
        this.queryRepository = queryRepository;
        this.customerCommandRepository = customerCommandRepository;
        this.transactionManager = transactionManager;
        this.countryQueryRepository = countryQueryRepository;
    }

    public List<Map<String,Object>> fetchAllCustomers() {

        return queryRepository.fetchAll();
    }

    public boolean deleteCustomer(Long customerId) throws Exception {
        boolean isDeleted = transactionManager.execute(con -> {
            int deleted = customerCommandRepository.delete(con, customerId);
            if (deleted == 0) {
                throw new IllegalStateException(
                        "Customer not found:  " + customerId
                );
            }
            return true;
        });
        return isDeleted;
    }

    public void createCustomer(CustomerCreateRequest request) throws Exception {

        transactionManager.execute(con -> {

            Long countryId= countryQueryRepository.fetchActiveCountryId(request.getCountryCode(),request.getEffectiveStartDate());
            log.info("Country Id: {}",countryId);
            customerCommandRepository.insert(con,Map.of(
                    "customerNumber", request.getCustomerNumber(),
                    "fullName", request.getFullName(),
                    "countryId", countryId,
                    "effectiveStartDate", request.getEffectiveStartDate()
            ));
            return null;
        });
    }
}
