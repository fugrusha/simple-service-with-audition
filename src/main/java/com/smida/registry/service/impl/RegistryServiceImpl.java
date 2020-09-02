package com.smida.registry.service.impl;

import com.smida.registry.domain.Registry;
import com.smida.registry.domain.RegistryStatus;
import com.smida.registry.dto.*;
import com.smida.registry.exception.EntityAlreadyExistsException;
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
        Registry registry = getRegistryRequired(id);
        return translationService.translate(registry, RegistryReadDto.class);
    }

    @Override
    public RegistryReadDto createRegistry(RegistryCreateDto createDto) {
        if (registryRepository.findByUsreou(createDto.getUsreou()) != null) {
            throw new EntityAlreadyExistsException(Registry.class,
                    createDto.getUsreou());
        }

        Registry registry = translationService
                .translate(createDto, Registry.class);

        registry.setStatus(RegistryStatus.ACTIVE);
        registry.setTotalValue(
                createDto.getNominalValue() * createDto.getQuantity());

        registry = registryRepository.save(registry);
        return translationService.translate(registry, RegistryReadDto.class);
    }

    @Override
    public RegistryReadDto updateRegistry(UUID id, RegistryPutDto putDto) {
        Registry registry = getRegistryRequired(id);

        translationService.map(putDto, registry);
        registry = registryRepository.save(registry);

        return translationService.translate(registry, RegistryReadDto.class);
    }

    @Override
    public RegistryReadDto patchRegistry(UUID id, RegistryPatchDto patchDto) {
        Registry registry = getRegistryRequired(id);

        translationService.map(patchDto, registry);
        registry = registryRepository.save(registry);

        return translationService.translate(registry, RegistryReadDto.class);
    }

    @Override
    public RegistryReadDto deleteRegistry(UUID id) {
        Registry registry = getRegistryRequired(id);
        registry.setStatus(RegistryStatus.DELETED);

        registry = registryRepository.save(registry);
        return translationService.translate(registry, RegistryReadDto.class);
    }

    private Registry getRegistryRequired(UUID id) {
        return registryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(Registry.class, id));
    }
}
