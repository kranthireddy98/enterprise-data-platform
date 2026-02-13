package com.EnterprisePlatform.service;

import com.EnterprisePlatform.DTO.BulkCustomerRow;
import com.EnterprisePlatform.DTO.BulkUploadRequest;
import com.EnterprisePlatform.DTO.BulkUploadResponse;
import com.EnterprisePlatform.bulk.BulkCustomerProcessor;
import com.EnterprisePlatform.repository.command.*;
import com.EnterprisePlatform.transaction.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BulkProcessingService {

    private static final Logger log = LoggerFactory.getLogger(BulkProcessingService.class);

    private final TransactionManager txManager;
    private final BulkCustomerProcessor processor;

    private final CustomerCommandRepository customerRepo;
    private final BillingAccountCommandRepository billingRepo;

    private final BulkJobRepository jobRepo;
    private final BulkRowRepository rowRepo;
    private final BulkErrorRepository errorRepo;

    public BulkProcessingService(TransactionManager txManager, BulkCustomerProcessor processor, CustomerCommandRepository customerRepo, BillingAccountCommandRepository billingRepo, BulkJobRepository jobRepo, BulkRowRepository rowRepo, BulkErrorRepository errorRepo) {
        this.txManager = txManager;
        this.processor = processor;
        this.customerRepo = customerRepo;
        this.billingRepo = billingRepo;
        this.jobRepo = jobRepo;
        this.rowRepo = rowRepo;
        this.errorRepo = errorRepo;
    }

    public BulkUploadResponse process(BulkUploadRequest request) {

        Long jobId = jobRepo.createJob(
                "CUSTOMER_UPLOAD",
                request.uploadedBy(),
                request.rows().size()
        );
        log.info("Job Created for the Upload request by: {}",request.uploadedBy());
        int success = 0;
        int failed = 0;

        for(int i =0;i <request.rows().size();i++){
            int rowNumber = i +1;
            BulkCustomerRow row = request.rows().get(i);

            Long rowId = rowRepo.createRow(jobId,rowNumber);
            log.info("Row Id Generated: {}",request.uploadedBy());
            try {
                txManager.execute(con -> {
                    var validated = processor.validateAndResolve(row);

                    Long customerId = customerRepo.insert(con,validated);

                    log.info(String.valueOf(customerId));
                    billingRepo.insert(con,customerId,validated);

                    return  null;
                });

                rowRepo.markSuccess(rowId);
                success++;
            }catch (Exception ex){
                log.error(String.valueOf(ex));
                errorRepo.recordError(
                        rowId,
                        "ROW_ERROR",
                        ex.getMessage()
                );

                rowRepo.markFailed(rowId);
                failed++;
            }
        }

        jobRepo.completeJob(
                jobId,
                success,
                failed,
                LocalDateTime.now()
        );

        return  new BulkUploadResponse(
                jobId,
                request.rows().size(),
                success,
                failed,
                "COMPLETED"
        );
    }
}
