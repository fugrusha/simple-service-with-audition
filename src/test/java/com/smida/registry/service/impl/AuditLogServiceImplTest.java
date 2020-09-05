package com.smida.registry.service.impl;

import com.smida.registry.domain.AuditLog;
import com.smida.registry.dto.AuditLogDto;
import com.smida.registry.dto.PageResult;
import com.smida.registry.dto.RegistryCreateDto;
import com.smida.registry.dto.RegistryReadDto;
import com.smida.registry.repository.AuditLogRepository;
import com.smida.registry.service.AuditLogService;
import com.smida.registry.service.RegistryService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Sql(statements = {
        "delete from registry",
        "delete from audit_log"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuditLogServiceImplTest {

    @Autowired
    private RegistryService registryService;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private AuditLogRepository auditLogRepository;

    private final String registryUsreou = "1546545";
    private UUID registryId;

    @Before
    public void createRegistry() {
        RegistryCreateDto createDto = new RegistryCreateDto();
        createDto.setComment("new comment");
        createDto.setDateOfIssue(LocalDate.of(1990, 2, 12));
        createDto.setNominalValue(20.0);
        createDto.setUsreou(registryUsreou);
        createDto.setQuantity(5.0);

        RegistryReadDto readDto = registryService.createRegistry(createDto);

        registryId = readDto.getId();
    }

    @Test
    public void testLog() {
        Assert.assertEquals(7, auditLogRepository.count());

        Page<AuditLog> logRecords = auditLogRepository
                .findByUsreou(registryUsreou, Pageable.unpaged());

        Assert.assertNotNull(logRecords);
    }

    @Test
    public void testGetLogByObjectId() {
        PageResult<AuditLogDto> logs = auditLogService
                .getLogByObjectId(registryId, Pageable.unpaged());

        Assertions.assertThat(logs.getData())
                .extracting("usreou")
                .containsOnly(registryUsreou);
    }
}
