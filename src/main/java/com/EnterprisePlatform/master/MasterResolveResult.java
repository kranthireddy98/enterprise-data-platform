package com.EnterprisePlatform.master;

import java.time.LocalDate;

public record MasterResolveResult(
       Long id,
       String code,
       MasterType type
) {
}
