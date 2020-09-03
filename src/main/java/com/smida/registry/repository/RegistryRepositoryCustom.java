package com.smida.registry.repository;

import com.smida.registry.domain.Registry;
import com.smida.registry.dto.RegistryFilter;

import java.util.List;

public interface RegistryRepositoryCustom {

    List<Registry> findByFilter(RegistryFilter filter);
}
