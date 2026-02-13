package com.EnterprisePlatform.bulk;

import com.EnterprisePlatform.DTO.BulkCustomerRow;
import com.EnterprisePlatform.DTO.ResolvedCustomerData;
import com.EnterprisePlatform.master.MasterResolver;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BulkCustomerProcessorTest {

    @Test
    void validation_shouldFail_invalidCountry(){

        MasterResolver resolver = mock(MasterResolver.class);

        when(resolver.resolve(any()))
                .thenThrow(new RuntimeException("Not found"));

        BulkCustomerProcessor processor = new BulkCustomerProcessor(resolver);

        BulkCustomerRow row = new BulkCustomerRow(
                "C001",
                "John Doe",
                "XX",
                "USD",
                        "CARD",
                LocalDate.now(),
                null,
                null
        );

        ResolvedCustomerData result = processor.validateAndResolve(row);


    }
}
