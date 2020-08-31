package com.smida.registry.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Registry {

    @Id
    @GeneratedValue
    private UUID id;

    // max length 8
    private Integer usreou;

    private String comment;

    private Double quantity;

    private Double nominalValue;

    private Double totalValue;

    private Date dateOfIssue;

    @Enumerated(EnumType.STRING)
    private RegistryStatus status;
}
