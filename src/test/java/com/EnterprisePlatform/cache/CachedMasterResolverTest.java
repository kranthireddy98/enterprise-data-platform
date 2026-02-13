package com.EnterprisePlatform.cache;

import com.EnterprisePlatform.master.DefaultMasterResolver;
import com.EnterprisePlatform.master.MasterResolveResult;
import com.EnterprisePlatform.master.MasterResolverRequest;
import com.EnterprisePlatform.master.MasterType;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CachedMasterResolverTest {

    @Test
    void cacheHit_ShouldNotCallDelegate(){

        DefaultMasterResolver delegate = mock(DefaultMasterResolver.class);

        Cache<MasterCacheKey,Long> cache = Caffeine.newBuilder().build();

        CachedMasterResolver resolver=new CachedMasterResolver(delegate,cache);

        MasterResolverRequest request = new MasterResolverRequest(
                MasterType.COUNTRY,
                "IN",
                LocalDate.now()
        );

        MasterCacheKey key= new MasterCacheKey(
                request.type(),
                request.code(),
                request.asOfDate()
        );

        cache.put(key,101L);

        MasterResolveResult result = resolver.resolve(request);

        assertEquals(101L,result.id());
        verify(delegate,never()).resolve(any());
    }
}
