package com.EnterprisePlatform.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BulkCustomerRow(
        String customerNumber,
        String fullName,
        String countryCode,

        String currencyCode,
        String paymentMethodCode,

        LocalDate effectiveStartDate,
        LocalDate effectiveEndDate,

        BigDecimal baseAmount
) {
}
