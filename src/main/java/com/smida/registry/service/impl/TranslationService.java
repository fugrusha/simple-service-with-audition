package com.smida.registry.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranslationService {

    @Autowired
    private ModelMapper mapper;

    public <T> T translate(Object srcObj, Class<T> destClass) {
        return mapper.map(srcObj, destClass);
    }

    public void map(Object srcObj, Object destObj) {
        mapper.map(srcObj, destObj);
    }

    public <T> List<T> translateToList(
            List<?> srcList,
            Class<T> destClass
    ) {
        return srcList.stream()
                .map(el -> translate(el, destClass))
                .collect(Collectors.toList());
    }
}
