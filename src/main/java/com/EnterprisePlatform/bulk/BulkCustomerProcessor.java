package com.EnterprisePlatform.bulk;

import com.EnterprisePlatform.DTO.BulkCustomerRow;
import com.EnterprisePlatform.DTO.ResolvedCustomerData;
import com.EnterprisePlatform.master.MasterResolver;
import com.EnterprisePlatform.master.MasterResolverRequest;
import com.EnterprisePlatform.master.MasterType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class BulkCustomerProcessor {

    private final MasterResolver masterResolver;


    public BulkCustomerProcessor(MasterResolver masterResolver) {
        this.masterResolver = masterResolver;
    }




    public ResolvedCustomerData validateAndResolve(BulkCustomerRow row){

        if(row.customerNumber() ==null || row.customerNumber().isBlank()){
           throw new IllegalStateException("Customer number is mandatory");
        }

        if(row.effectiveEndDate() !=null &&
        row.effectiveEndDate().isBefore(row.effectiveStartDate())){
                throw  new IllegalStateException("Effective end date cannot be before start date");
        }

        Long countryId = masterResolver.resolve(
                    new MasterResolverRequest(
                            MasterType.COUNTRY,
                            row.countryCode(),
                            LocalDate.now()
                    )
            ).id();

        log.info(String.valueOf(countryId));

        Long currencyId = masterResolver.resolve(
                new MasterResolverRequest(
                        MasterType.CURRENCY,
                        row.currencyCode(),
                        LocalDate.now()
                )
        ).id();

        log.info(String.valueOf(countryId));

        Long paymentMethodId = masterResolver.resolve(
                new MasterResolverRequest(
                        MasterType.PAYMENT_METHOD,
                        row.paymentMethodCode(),
                        LocalDate.now()
                )
        ).id();

        log.info(String.valueOf(countryId));

        return new ResolvedCustomerData(
                row.customerNumber(),
                row.fullName(),
                countryId,
                currencyId,
                paymentMethodId,
                row.effectiveStartDate(),
                row.effectiveEndDate()
        );
    }
}
