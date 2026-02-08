package com.EnterprisePlatform.master;

import com.EnterprisePlatform.master.exception.MasterDataCorruptionException;
import com.EnterprisePlatform.master.exception.MasterDataNotFoundException;
import com.EnterprisePlatform.repository.query.MasterQueryRepository;
import jakarta.validation.constraints.Max;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class DefaultMasterResolverTest {

    private final MasterQueryRepository repository =
            Mockito.mock(MasterQueryRepository.class);

    private final MasterResolver resolver = new DefaultMasterResolver(repository);

    @Test
    void resolve_success_singleRow(){
        when(repository.findActiveByCode(
                MasterType.COUNTRY,
                "IN",
                LocalDate.now()
        )).thenReturn(
                List.of(Map.of("id",101L))
        );

        MasterResolveResult result = resolver.resolve(
                new MasterResolverRequest(
                        MasterType.COUNTRY,
                        "IN",
                        LocalDate.now()
                )
        );

        System.out.println(result);
        assertEquals(101L,result.id());
        assertEquals("IN",result.code());
        assertEquals(MasterType.COUNTRY,result.type());
    }

    @Test
    void resolve_notFound(){
        when(repository.findActiveByCode(
                MasterType.COUNTRY,
                "XX",
                LocalDate.now()
        )).thenReturn(List.of());

        assertThrows(
                MasterDataNotFoundException.class,
                () -> resolver.resolve(
                        new MasterResolverRequest(
                                MasterType.COUNTRY,
                                "XX",
                                LocalDate.now()
                        )
                )
        );
    }

    @Test
    void resolve_corruption_multipleRows() {

        when(repository.findActiveByCode(
                MasterType.COUNTRY,
                "IN",
                LocalDate.now()
        )).thenReturn(
                List.of(
                        Map.of("id",2L),
                        Map.of("id",1L)
                )
        );

        assertThrows(
                MasterDataCorruptionException.class,
                () -> resolver.resolve(
                        new MasterResolverRequest(
                                MasterType.COUNTRY,
                                "IN",
                                LocalDate.now()
                        )
                )
        );
    }
}
