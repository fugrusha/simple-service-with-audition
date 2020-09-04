package com.smida.registry.dto;

import com.smida.registry.domain.RegistryStatus;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
public class RegistryFilter {

    private Set<RegistryStatus> statuses;

    @Pattern(regexp = "\\d{8}")
    private String usreou;

}
