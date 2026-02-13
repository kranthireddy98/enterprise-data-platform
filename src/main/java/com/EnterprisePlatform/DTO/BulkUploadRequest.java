package com.EnterprisePlatform.DTO;

import java.util.List;

public record BulkUploadRequest(
        String uploadedBy,
        List<BulkCustomerRow> rows
) {
}
