package com.smida.registry.service.impl;

import com.smida.registry.domain.AuditLog;
import com.smida.registry.dto.RegistryCreateDto;
import com.smida.registry.repository.AuditLogRepository;
import com.smida.registry.service.RegistryService;
import org.junit.Assert;
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
    private AuditLogRepository auditLogRepository;

    @Test
    public void testLog() {
        RegistryCreateDto createDto = new RegistryCreateDto();
        createDto.setComment("new comment");
        createDto.setDateOfIssue(LocalDate.of(1990, 2, 12));
        createDto.setNominalValue(20.0);
        createDto.setUsreou("1546545");
        createDto.setQuantity(5.0);

        registryService.createRegistry(createDto);

        Assert.assertEquals(7, auditLogRepository.count());

        Page<AuditLog> logRecords = auditLogRepository
                .findByUsreou(createDto.getUsreou(), Pageable.unpaged());

        Assert.assertNotNull(logRecords);
    }
}
