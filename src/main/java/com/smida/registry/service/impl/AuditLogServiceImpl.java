package com.smida.registry.service.impl;

import com.smida.registry.domain.AuditLog;
import com.smida.registry.dto.AuditLogDto;
import com.smida.registry.repository.AuditLogRepository;
import com.smida.registry.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private TranslationService translationService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(AuditLogDto auditLogDto) {

        AuditLog record = translationService.translate(auditLogDto, AuditLog.class);
        record.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(record);
    }
}
