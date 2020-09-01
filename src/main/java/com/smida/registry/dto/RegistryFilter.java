package com.smida.registry.dto;

import com.smida.registry.domain.RegistryStatus;
import lombok.Data;

@Data
public class RegistryFilter {

    private RegistryStatus status;

    private String usreou;

}
