package com.smida.registry.service;

import com.smida.registry.dto.AuditLogDto;
import com.smida.registry.dto.PageResult;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AuditLogService {

    void log(AuditLogDto auditLogDto);

    PageResult<AuditLogDto> getLogByObjectId(UUID id, Pageable pageable);
}
