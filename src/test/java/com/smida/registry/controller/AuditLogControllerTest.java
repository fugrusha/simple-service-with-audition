package com.smida.registry.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smida.registry.dto.AuditLogDto;
import com.smida.registry.dto.PageResult;
import com.smida.registry.service.AuditLogService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuditLogController.class)
public class AuditLogControllerTest {

    @MockBean
    private AuditLogService auditLogService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.data.web.pageable.default-page-size}")
    private int defaultPageSize;

    @Test
    public void testGetLogByObjectId() throws Exception {
        UUID objectId = UUID.randomUUID();
        AuditLogDto readDto = createAuditLogDto(objectId);

        List<AuditLogDto> logDtos = new ArrayList<>();
        logDtos.add(readDto);

        PageResult<AuditLogDto> pageResult = new PageResult<>();
        pageResult.setData(logDtos);

        PageRequest pageRequest = PageRequest.of(0, defaultPageSize);
        Mockito.when(auditLogService.getLogByObjectId(objectId, pageRequest))
                .thenReturn(pageResult);

        String resultJson = mockMvc
                .perform(get("/api/v1/audit-logs/{id}", objectId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PageResult<AuditLogDto> actualResult = objectMapper
                .readValue(resultJson, new TypeReference<PageResult<AuditLogDto>>() {});

        Assert.assertEquals(pageResult, actualResult);

        Mockito.verify(auditLogService).getLogByObjectId(objectId, pageRequest);
    }

    private AuditLogDto createAuditLogDto(UUID objectId) {
        AuditLogDto dto = new AuditLogDto();
        dto.setPersistedObjectId(objectId.toString());
        dto.setClassName("Registry");
        dto.setEventName("UPDATE");
        dto.setPropertyName("nominalValue");
        dto.setNewValue("40.0");
        dto.setOldValue("20.0");
        dto.setUsreou("12345678");
        return dto;
    }
}
