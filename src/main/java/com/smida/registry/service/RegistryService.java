package com.smida.registry.service;

import com.smida.registry.dto.*;

import java.util.List;
import java.util.UUID;

public interface RegistryService {

    List<RegistryReadDto> getRegistries(RegistryFilter filter);

    RegistryReadDto getRegistryById(UUID id);

    RegistryReadDto createRegistry(RegistryCreateDto createDto);

    RegistryReadDto updateRegistry(UUID id, RegistryPutDto putDto);

    RegistryReadDto patchRegistry(UUID id, RegistryPatchDto patchDto);

    RegistryReadDto deleteRegistry(UUID id);
}
