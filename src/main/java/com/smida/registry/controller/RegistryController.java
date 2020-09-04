package com.smida.registry.controller;

import com.smida.registry.dto.*;
import com.smida.registry.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/registries")
public class RegistryController {

    @Autowired
    private RegistryService registryService;

    @GetMapping
    public PageResult<RegistryReadDto> getRegistries(
            RegistryFilter filter,
            Pageable pageable
    ) {
        return registryService.getRegistries(filter, pageable);
    }

    @GetMapping("/{id}")
    public RegistryReadDto getRegistryById(@PathVariable UUID id) {
        return registryService.getRegistryById(id);
    }

    @PostMapping
    public RegistryReadDto createRegistry(@RequestBody RegistryCreateDto createDto) {
        return registryService.createRegistry(createDto);
    }

    @PutMapping("/{id}")
    public RegistryReadDto updateRegistry(
            @PathVariable UUID id,
            @RequestBody RegistryPutDto putDto
    ) {
        return registryService.updateRegistry(id, putDto);
    }

    @PatchMapping("/{id}")
    public RegistryReadDto patchRegistry(
            @PathVariable UUID id,
            @RequestBody RegistryPatchDto patchDto
    ) {
        return registryService.patchRegistry(id, patchDto);
    }

    @DeleteMapping("/{id}")
    public RegistryReadDto deleteRegistry(@PathVariable UUID id) {
        return registryService.deleteRegistry(id);
    }
}
