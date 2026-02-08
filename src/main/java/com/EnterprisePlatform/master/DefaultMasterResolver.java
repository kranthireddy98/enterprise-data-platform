package com.EnterprisePlatform.master;

import com.EnterprisePlatform.master.exception.MasterDataCorruptionException;
import com.EnterprisePlatform.master.exception.MasterDataNotFoundException;
import com.EnterprisePlatform.repository.query.MasterQueryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DefaultMasterResolver implements MasterResolver{

    private final MasterQueryRepository repository;

    public DefaultMasterResolver(MasterQueryRepository repository) {
        this.repository = repository;
    }


    @Override
    public MasterResolveResult resolve(MasterResolverRequest request) {

        List<Map<String,Object>> rows = repository.findActiveByCode(
                request.type(),
                request.code(),
                request.asOfDate()
        );

        if(rows.isEmpty()){
            throw new MasterDataNotFoundException(
                    request.type().name(),
                    request.code()
            );

        }

        if(rows.size()>1){
            throw new MasterDataCorruptionException(
                    request.type().name(),
                    request.code()
                    );
        }

        Map<String,Object> row = rows.get(0);

       long id = ((Number)row.get("id")).longValue();

        return new MasterResolveResult(
                id,
                request.code(),
                request.type()
        );
    }
}
