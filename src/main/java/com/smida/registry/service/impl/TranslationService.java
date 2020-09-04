package com.smida.registry.service.impl;

import com.smida.registry.dto.PageResult;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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

    public <E, T> PageResult<T> toPageResult(Page<E> page, Class<T> dtoType) {
        PageResult<T> result = new PageResult<>();
        result.setPage(page.getNumber());
        result.setPageSize(page.getSize());
        result.setTotalElements(page.getTotalElements());
        result.setTotalPages(page.getTotalPages());
        result.setData(page.getContent().stream()
                .map(e -> translate(e, dtoType))
                .collect(Collectors.toList()));
        return result;
    }
}
