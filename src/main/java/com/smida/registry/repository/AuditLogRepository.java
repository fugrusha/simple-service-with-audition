package com.smida.registry.repository;

import com.smida.registry.domain.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuditLogRepository extends CrudRepository<AuditLog, UUID> {

    Page<AuditLog> findByUsreou(String usreou, Pageable pageable);

    Page<AuditLog> findByPersistedObjectId(String objectId, Pageable pageable);
}
