package com.smida.registry.service.impl;

import com.smida.registry.domain.Registry;
import com.smida.registry.dto.*;
import com.smida.registry.exception.EntityNotFoundException;
import com.smida.registry.repository.RegistryRepository;
import com.smida.registry.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RegistryServiceImpl implements RegistryService {

    @Autowired
    private RegistryRepository registryRepository;

    @Autowired
    private TranslationService translationService;

    @Override
    public List<RegistryReadDto> getRegistriesByFilter(RegistryFilter filter) {
        return null;
    }

    @Override
    public RegistryReadDto getRegistryById(UUID id) {
        Registry registry = registryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(Registry.class, id));
        return translationService.toRead(registry, RegistryReadDto.class);
    }

    @Override
    public RegistryReadDto createRegistry(RegistryCreateDto createDto) {
        return null;
    }

    @Override
    public RegistryReadDto updateRegistry(UUID id, RegistryPutDto putDto) {
        return null;
    }

    @Override
    public RegistryReadDto patchRegistry(UUID id, RegistryPatchDto patchDto) {
        return null;
    }

    @Override
    public RegistryReadDto deleteRegistry(UUID id) {
        return null;
    }
}
