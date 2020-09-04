package com.smida.registry.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private List<T> data;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
}
