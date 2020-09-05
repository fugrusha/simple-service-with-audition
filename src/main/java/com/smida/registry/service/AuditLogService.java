package com.smida.registry.service;

import com.smida.registry.dto.AuditLogDto;

public interface AuditLogService {

    void log(AuditLogDto auditLogDto);
}
