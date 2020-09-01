package com.smida.registry.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

//    public RegistryReadDto toRead(Registry registry) {
//        RegistryReadDto dto = new RegistryReadDto();
//        dto.setId(registry.getId());
//        dto.setStatus(registry.getStatus());
//        dto.setTotalValue(registry.getTotalValue());
//        dto.setNominalValue(registry.getNominalValue());
//        dto.setQuantity(registry.getQuantity());
//        dto.setComment(registry.getComment());
//        dto.setUsreou(registry.getUsreou());
//        dto.setDateOfIssue(registry.getDateOfIssue());
//        return dto;
//    }

    public <T> T toRead(Object srcObj, Class<T> destClass) {
        return new ModelMapper().map(srcObj, destClass);
    }
}
