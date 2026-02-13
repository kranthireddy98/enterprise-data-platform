package com.EnterprisePlatform.master;

import com.EnterprisePlatform.exception.MasterDataCorruptionException;
import com.EnterprisePlatform.exception.MasterDataNotFoundException;
import com.EnterprisePlatform.repository.query.MasterQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DefaultMasterResolver implements MasterResolver{

    private static final Logger log = LoggerFactory.getLogger(DefaultMasterResolver.class);
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
        log.info("Master rows for type: {}, data: {}",request.type(),rows);
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
