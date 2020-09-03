package com.smida.registry.dto;

import com.smida.registry.domain.RegistryStatus;
import lombok.Data;

import java.util.Set;

@Data
public class RegistryFilter {

    private Set<RegistryStatus> statuses;

    private String usreou;

}
