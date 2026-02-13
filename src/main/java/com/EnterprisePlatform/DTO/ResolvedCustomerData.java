package com.EnterprisePlatform.DTO;

public record ResolvedCustomerData(
        String customerNumber,
        String fullName,
        Long countryId,
        Long currencyId,
        Long paymentMethodId,
        java.time.LocalDate effectiveStartDate,
        java.time.LocalDate effectiveEndDate
) {}
