package com.EnterprisePlatform.DTO;

public record BulkUploadResponse(
        Long jobId,
        int totalRows,
        int successRows,
        int failedRows,
        String status
) {
}
