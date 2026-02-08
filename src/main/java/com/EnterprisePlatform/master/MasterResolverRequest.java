package com.EnterprisePlatform.master;

import java.time.LocalDate;

public record MasterResolverRequest (
        MasterType type,
        String code,
        LocalDate asOfDate
){ }
