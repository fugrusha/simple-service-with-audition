package com.smida.registry.repository;

import com.smida.registry.domain.Registry;
import com.smida.registry.dto.RegistryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RegistryRepositoryCustom {

    Page<Registry> findByFilter(RegistryFilter filter, Pageable pageable);
}
