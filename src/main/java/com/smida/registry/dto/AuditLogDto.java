package com.smida.registry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDto {

    private String usreou;

    private String className;

    private String persistedObjectId;

    private String eventName;

    private String propertyName;

    private String oldValue;

    private String newValue;
}
