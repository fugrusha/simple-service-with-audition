package com.smida.registry.controller;

import com.smida.registry.dto.AuditLogDto;
import com.smida.registry.dto.PageResult;
import com.smida.registry.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping("/{id}")
    public PageResult<AuditLogDto> getLogByObjectId(
            @PathVariable UUID id,
            Pageable pageable
    ) {
        return auditLogService.getLogByObjectId(id, pageable);
    }
}
