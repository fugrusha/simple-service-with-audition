package com.smida.registry.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class RegistryPutDto {

    @Pattern(regexp = "\\d{8}")
    private String usreou;

    private String comment;

    private Double quantity;

    private Double nominalValue;

    private LocalDate dateOfIssue;
}
