package com.smida.registry.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class RegistryCreateDto {

    @NotNull
    @Pattern(regexp = "\\d{8}")
    private String usreou;

    private String comment;

    @NotNull
    private Double quantity;

    @NotNull
    private Double nominalValue;

    @NotNull
    private LocalDate dateOfIssue;
}
