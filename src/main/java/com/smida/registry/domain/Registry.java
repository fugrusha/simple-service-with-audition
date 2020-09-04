package com.smida.registry.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Registry {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String usreou;

    private String comment;

    private Double quantity;

    private Double nominalValue;

    private Double totalValue;

    private LocalDate dateOfIssue;

    @Enumerated(EnumType.STRING)
    private RegistryStatus status;
}
