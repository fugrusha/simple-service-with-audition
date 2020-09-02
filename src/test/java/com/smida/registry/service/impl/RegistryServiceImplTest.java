package com.smida.registry.service.impl;

import com.smida.registry.domain.Registry;
import com.smida.registry.domain.RegistryStatus;
import com.smida.registry.dto.RegistryCreateDto;
import com.smida.registry.dto.RegistryPatchDto;
import com.smida.registry.dto.RegistryPutDto;
import com.smida.registry.dto.RegistryReadDto;
import com.smida.registry.exception.EntityAlreadyExistsException;
import com.smida.registry.exception.EntityNotFoundException;
import com.smida.registry.repository.RegistryRepository;
import com.smida.registry.service.RegistryService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
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
@Sql(statements = {
        "delete from registry" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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

    @Test
    public void testCreateRegistry() {
        RegistryCreateDto createDto = new RegistryCreateDto();
        createDto.setComment("new comment");
        createDto.setDateOfIssue(LocalDate.of(1990, 2, 12));
        createDto.setNominalValue(20.0);
        createDto.setUsreou("1546545");
        createDto.setQuantity(5.0);

        RegistryReadDto readDto = registryService.createRegistry(createDto);

        Assert.assertNotNull(readDto.getId());
        Assertions.assertThat(createDto)
                .isEqualToComparingFieldByField(readDto);

        Registry registry = registryRepository.findById(readDto.getId()).get();
        Assertions.assertThat(readDto).isEqualToComparingFieldByField(registry);
    }

    @Test
    public void testCreateRegistryAlreadyExists() {
        RegistryCreateDto createDto = new RegistryCreateDto();
        createDto.setComment("new comment");
        createDto.setDateOfIssue(LocalDate.of(1990, 2, 12));
        createDto.setNominalValue(20.0);
        createDto.setUsreou("1546545");
        createDto.setQuantity(5.0);

        registryService.createRegistry(createDto);

        Assertions.assertThatThrownBy(() -> {
            registryService.createRegistry(createDto);
        }).isInstanceOf(EntityAlreadyExistsException.class);
    }

    @Test
    public void testUpdateRegistry() {
        Registry registry = createRegistry();

        RegistryPutDto putDto = new RegistryPutDto();
        putDto.setComment("new comment");
        putDto.setDateOfIssue(LocalDate.of(1990, 2, 12));
        putDto.setNominalValue(20.0);
        putDto.setUsreou("1546545");
        putDto.setQuantity(5.0);

        RegistryReadDto readDto = registryService
                .updateRegistry(registry.getId(), putDto);

        Assertions.assertThat(putDto).isEqualToComparingFieldByField(readDto);

        Registry updatedRegistry = registryRepository.findById(readDto.getId()).get();
        Assertions.assertThat(readDto).isEqualToComparingFieldByField(updatedRegistry);
    }

    @Test
    public void testPatchRegistry() {
        Registry registry = createRegistry();

        RegistryPatchDto patchDto = new RegistryPatchDto();
        patchDto.setComment("new comment");
        patchDto.setDateOfIssue(LocalDate.of(1990, 2, 12));
        patchDto.setNominalValue(20.0);
        patchDto.setUsreou("1546545");
        patchDto.setQuantity(5.0);

        RegistryReadDto readDto = registryService
                .patchRegistry(registry.getId(), patchDto);

        Assertions.assertThat(patchDto).isEqualToComparingFieldByField(readDto);

        Registry updatedRegistry = registryRepository.findById(readDto.getId()).get();
        Assertions.assertThat(readDto).isEqualToComparingFieldByField(updatedRegistry);
    }

    @Test
    public void testPatchRegistryEmptyPatch() {
        Registry registry = createRegistry();

        RegistryPatchDto patchDto = new RegistryPatchDto();

        RegistryReadDto readDto = registryService
                .patchRegistry(registry.getId(), patchDto);

        Assertions.assertThat(readDto).hasNoNullFieldsOrProperties();

        Registry patchedRegistry = registryRepository.findById(readDto.getId()).get();
        Assertions.assertThat(registry).isEqualToComparingFieldByField(patchedRegistry);
    }

    @Test
    public void testDeleteRegistry() {
        Registry registry = createRegistry();

        RegistryReadDto readDto = registryService.deleteRegistry(registry.getId());
        Assert.assertEquals(RegistryStatus.DELETED, readDto.getStatus());

        Registry deletedRegistry = registryRepository.findById(readDto.getId()).get();
        Assert.assertEquals(RegistryStatus.DELETED, deletedRegistry.getStatus());
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
