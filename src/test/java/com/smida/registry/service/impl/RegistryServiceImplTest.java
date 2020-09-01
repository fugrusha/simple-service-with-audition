package com.smida.registry.service.impl;

import com.smida.registry.domain.Registry;
import com.smida.registry.domain.RegistryStatus;
import com.smida.registry.dto.RegistryReadDto;
import com.smida.registry.exception.EntityNotFoundException;
import com.smida.registry.repository.RegistryRepository;
import com.smida.registry.service.RegistryService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Sql(statements = {"delete from registry"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RegistryServiceImplTest {

    @Autowired
    private RegistryRepository registryRepository;

    @Autowired
    private RegistryService registryService;

    @Test
    public void testGetRegistryById() {
        Registry registry = createRegistry();

        RegistryReadDto readDto = registryService
                .getRegistryById(registry.getId());

        Assertions.assertThat(readDto).isEqualToComparingFieldByField(registry);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetRegistryByIdWrongId() {
        registryService.getRegistryById(UUID.randomUUID());
    }

    private Registry createRegistry() {
        Registry registry = new Registry();
        registry.setDateOfIssue(LocalDate.of(2000, 10, 20));
        registry.setUsreou("123456465");
        registry.setComment("some text");
        registry.setStatus(RegistryStatus.ACTIVE);
        registry.setQuantity(20.0);
        registry.setNominalValue(10.0);
        registry.setTotalValue(200.0);
        return registryRepository.save(registry);
    }
}
