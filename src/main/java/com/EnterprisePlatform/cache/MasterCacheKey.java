package com.EnterprisePlatform.cache;

import com.EnterprisePlatform.master.MasterType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MasterCacheKey(
        MasterType type,
        String code,
        LocalDate asOfDate
) {
}
