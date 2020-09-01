package com.smida.registry.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistryCreateDto {

    private String usreou;

    private String comment;

    private Double quantity;

    private Double nominalValue;

    private LocalDate dateOfIssue;
}
