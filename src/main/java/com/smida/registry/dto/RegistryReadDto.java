package com.smida.registry.dto;

import com.smida.registry.domain.RegistryStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class RegistryReadDto {

    private UUID id;

    private String usreou;

    private String comment;

    private Double quantity;

    private Double nominalValue;

    private Double totalValue;

    private LocalDate dateOfIssue;

    private RegistryStatus status;
}
