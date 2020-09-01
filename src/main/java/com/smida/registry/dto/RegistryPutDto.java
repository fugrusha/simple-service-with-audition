package com.smida.registry.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistryPutDto {

    private String usreou;

    private String comment;

    private Double quantity;

    private Double nominalValue;

    private LocalDate dateOfIssue;
}
