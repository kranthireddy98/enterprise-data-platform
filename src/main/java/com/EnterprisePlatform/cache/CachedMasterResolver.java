package com.EnterprisePlatform.cache;

import com.EnterprisePlatform.master.DefaultMasterResolver;
import com.EnterprisePlatform.master.MasterResolveResult;
import com.EnterprisePlatform.master.MasterResolver;
import com.EnterprisePlatform.master.MasterResolverRequest;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CachedMasterResolver implements MasterResolver {

    private final DefaultMasterResolver delegate;
    private final Cache<MasterCacheKey,Long> cache;

    public CachedMasterResolver(DefaultMasterResolver delegate, Cache<MasterCacheKey, Long> cache) {
        this.delegate = delegate;
        this.cache = cache;
    }

    @Override
    public MasterResolveResult resolve(MasterResolverRequest request) {

        MasterCacheKey key = new MasterCacheKey(
                request.type(),
                request.code(),
                request.asOfDate()
        );

        Long cacheId = cache.getIfPresent(key);

        if(cacheId!=null){
            System.out.println("From Cache");
            return new MasterResolveResult(
                    cacheId,
                    request.code(),
                    request.type()
            );

        }

        MasterResolveResult result=delegate.resolve(request);

        cache.put(key, result.id());

        return result;
    }
}
