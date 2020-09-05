package com.smida.registry.interceptor;

import com.smida.registry.dto.AuditLogDto;
import com.smida.registry.service.AuditLogService;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuditLogListener
        implements PostInsertEventListener, PostUpdateEventListener,
        PostDeleteEventListener {

    @Autowired
    private AuditLogService auditLogService;

    @Override
    public void onPostInsert(PostInsertEvent event) {
        Object entity = event.getEntity();
        if (entity instanceof Auditable) {
            List<AuditLogDto> auditTrailDTOList = new ArrayList<>();
            String[] propertyNames = event.getPersister().getPropertyNames();
            Object[] states = event.getState();

            for (int i = 0; i < propertyNames.length; i++) {
                auditTrailDTOList.add(new AuditLogDto(
                        ((Auditable) entity).getUsreou(),
                        entity.getClass().getCanonicalName(),
                        event.getId().toString(),
                        AuditEvent.INSERT.name(), propertyNames[i], null,
                        states[i].toString()));
            }

            auditTrailDTOList.forEach(auditLogService::log);
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return false;
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        Object entity = event.getEntity();
        if (entity instanceof Auditable) {
            String[] propertyNames = event.getPersister().getPropertyNames();
            Object[] currentState = event.getState();
            Object[] previousState = event.getOldState();
            List<AuditLogDto> auditTrailDTOList = new ArrayList<>();

            for (int i = 0; i < currentState.length; i++) {
                if (!previousState[i].equals(currentState[i])) {
                    auditTrailDTOList.add(new AuditLogDto(
                            ((Auditable) entity).getUsreou(),
                            entity.getClass().getCanonicalName(),
                            event.getId().toString(),
                            AuditEvent.UPDATE.name(), propertyNames[i],
                            previousState[i].toString(),
                            currentState[i].toString()));
                }
            }

            auditTrailDTOList.forEach(auditLogService::log);
        }
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        Object entity = event.getEntity();
        if (entity instanceof Auditable) {
            String[] propertyNames = event.getPersister().getPropertyNames();
            Object[] state = event.getDeletedState();
            List<AuditLogDto> auditTrailDTOList = new ArrayList<>();

            for (int i = 0; i < propertyNames.length; i++) {
                auditTrailDTOList.add(new AuditLogDto(
                        ((Auditable) entity).getUsreou(),
                        entity.getClass().getCanonicalName(),
                        event.getId().toString(), AuditEvent.DELETE.name(),
                        propertyNames[i], state[i].toString(), null));
            }

            auditTrailDTOList.forEach(auditLogService::log);
        }
    }
}
