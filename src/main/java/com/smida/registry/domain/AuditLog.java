package com.smida.registry.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class AuditLog {

    @Id
    @GeneratedValue
    private UUID id;

    private String usreou;

    private String className;

    private String persistedObjectId;

    private String eventName;

    private String propertyName;

    private String oldValue;

    private String newValue;

    private LocalDateTime timestamp;

}
