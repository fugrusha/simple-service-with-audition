package com.smida.registry.interceptor;

import com.smida.registry.dto.AuditLogDto;
import com.smida.registry.service.AuditLogService;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuditLogInterceptor extends EmptyInterceptor {

    @Autowired
    private AuditLogService auditLogService;

    @Override
    public boolean onFlushDirty(Object entity, Serializable id,
            Object[] currentState, Object[] previousState,
            String[] propertyNames, Type[] types) throws CallbackException {
        if (entity instanceof Auditable) {
            List<AuditLogDto> auditTrailDTOList = new ArrayList<>();

            for (int i = 0; i < currentState.length; i++) {
                if (!previousState[i].equals(currentState[i])) {
                    auditTrailDTOList.add(new AuditLogDto(
                            ((Auditable) entity).getUsreou(),
                            entity.getClass().getCanonicalName(), id.toString(),
                            AuditEvent.UPDATE.name(), propertyNames[i],
                            previousState[i].toString(),
                            currentState[i].toString()));
                }
            }

            auditTrailDTOList.forEach(auditLogService::log);
        }
        return true;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state,
            String[] propertyNames, Type[] types) throws CallbackException {
        if (entity instanceof Auditable) {
            List<AuditLogDto> auditTrailDTOList = new ArrayList<>();

            for (int i = 0; i < propertyNames.length; i++) {
                auditTrailDTOList.add(new AuditLogDto(
                        ((Auditable) entity).getUsreou(),
                        entity.getClass().getCanonicalName(), id.toString(),
                        AuditEvent.INSERT.name(), propertyNames[i], null,
                        state[i].toString()));
            }
            auditTrailDTOList.forEach(auditLogService::log);
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state,
            String[] propertyNames, Type[] types) throws CallbackException {
        if (entity instanceof Auditable) {
            List<AuditLogDto> auditTrailDTOList = new ArrayList<>();

            for (int i = 0; i < propertyNames.length; i++) {
                auditTrailDTOList.add(new AuditLogDto(
                        ((Auditable) entity).getUsreou(),
                        entity.getClass().getCanonicalName(), id.toString(),
                        AuditEvent.DELETE.name(), propertyNames[i],
                        state[i].toString(), null));
            }

            auditTrailDTOList.forEach(auditLogService::log);
        }
    }
}
