package com.smida.registry.service;

import com.smida.registry.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RegistryService {

    PageResult<RegistryReadDto> getRegistries(RegistryFilter filter, Pageable pageable);

    RegistryReadDto getRegistryById(UUID id);

    RegistryReadDto createRegistry(RegistryCreateDto createDto);

    RegistryReadDto updateRegistry(UUID id, RegistryPutDto putDto);

    RegistryReadDto patchRegistry(UUID id, RegistryPatchDto patchDto);

    RegistryReadDto deleteRegistry(UUID id);
}
