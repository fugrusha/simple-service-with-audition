package com.smida.registry.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smida.registry.domain.Registry;
import com.smida.registry.domain.RegistryStatus;
import com.smida.registry.dto.*;
import com.smida.registry.exception.EntityNotFoundException;
import com.smida.registry.exception.handler.ErrorInfo;
import com.smida.registry.service.RegistryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistryController.class)
public class RegistryControllerTest {

    @MockBean
    private RegistryService registryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.data.web.pageable.default-page-size}")
    private int defaultPageSize;

    @Test
    public void testGetRegistryById() throws Exception {
        RegistryReadDto readDto = createRegistryReadDto();

        Mockito.when(registryService.getRegistryById(readDto.getId()))
                .thenReturn(readDto);

        String resultJson = mockMvc
                .perform(get("/api/v1/registries/{id}", readDto.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        RegistryReadDto actualResult = objectMapper
                .readValue(resultJson, RegistryReadDto.class);

        Assert.assertEquals(readDto, actualResult);

        Mockito.verify(registryService).getRegistryById(readDto.getId());
    }

    @Test
    public void testCreateRegistry() throws Exception {
        RegistryReadDto readDto = createRegistryReadDto();

        RegistryCreateDto createDto = new RegistryCreateDto();
        createDto.setComment("new comment");
        createDto.setDateOfIssue(LocalDate.of(1990, 2, 12));
        createDto.setNominalValue(20.0);
        createDto.setUsreou("15465445");
        createDto.setQuantity(5.0);

        Mockito.when(registryService.createRegistry(createDto))
                .thenReturn(readDto);

        String resultJson = mockMvc
                .perform(post("/api/v1/registries/")
                .content(objectMapper.writeValueAsString(createDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        RegistryReadDto actualResult = objectMapper
                .readValue(resultJson, RegistryReadDto.class);

        Assert.assertEquals(readDto, actualResult);

        Mockito.verify(registryService).createRegistry(createDto);
    }

    @Test
    public void testUpdateRegistry() throws Exception {
        RegistryReadDto readDto = createRegistryReadDto();

        RegistryPutDto putDto = new RegistryPutDto();
        putDto.setComment("new comment");
        putDto.setDateOfIssue(LocalDate.of(1990, 2, 12));
        putDto.setNominalValue(20.0);
        putDto.setUsreou("15464545");
        putDto.setQuantity(5.0);

        Mockito.when(registryService.updateRegistry(readDto.getId(), putDto))
                .thenReturn(readDto);

        String resultJson = mockMvc
                .perform(put("/api/v1/registries/{id}", readDto.getId())
                .content(objectMapper.writeValueAsString(putDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        RegistryReadDto actualResult = objectMapper
                .readValue(resultJson, RegistryReadDto.class);

        Assert.assertEquals(readDto, actualResult);

        Mockito.verify(registryService).updateRegistry(readDto.getId(), putDto);
    }

    @Test
    public void testPatchRegistry() throws Exception {
        RegistryReadDto readDto = createRegistryReadDto();

        RegistryPatchDto patchDto = new RegistryPatchDto();
        patchDto.setComment("new comment");
        patchDto.setDateOfIssue(LocalDate.of(1990, 2, 12));
        patchDto.setNominalValue(20.0);
        patchDto.setUsreou("15465445");
        patchDto.setQuantity(5.0);

        Mockito.when(registryService.patchRegistry(readDto.getId(), patchDto))
                .thenReturn(readDto);

        String resultJson = mockMvc
                .perform(patch("/api/v1/registries/{id}", readDto.getId())
                .content(objectMapper.writeValueAsString(patchDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        RegistryReadDto actualResult = objectMapper
                .readValue(resultJson, RegistryReadDto.class);

        Assert.assertEquals(readDto, actualResult);

        Mockito.verify(registryService).patchRegistry(readDto.getId(), patchDto);
    }

    @Test
    public void testDeleteRegistry() throws Exception {
        RegistryReadDto readDto = createRegistryReadDto();

        Mockito.when(registryService.deleteRegistry(readDto.getId()))
                .thenReturn(readDto);

        String resultJson = mockMvc
                .perform(delete("/api/v1/registries/{id}",
                        readDto.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        RegistryReadDto actualResult = objectMapper
                .readValue(resultJson, RegistryReadDto.class);

        Assert.assertEquals(readDto, actualResult);

        Mockito.verify(registryService).deleteRegistry(readDto.getId());
    }

    @Test
    public void testGetRegistries() throws Exception {
        RegistryReadDto readDto = createRegistryReadDto();

        List<RegistryReadDto> registries = new ArrayList<>();
        registries.add(readDto);

        PageResult<RegistryReadDto> pageResult = new PageResult<>();
        pageResult.setData(registries);

        Set<RegistryStatus> statuses = new HashSet<>();
        statuses.add(RegistryStatus.DELETED);

        RegistryFilter filter = new RegistryFilter();
        filter.setStatuses(statuses);
        filter.setUsreou("12343235");

        PageRequest pageRequest = PageRequest.of(0, defaultPageSize);
        Mockito.when(registryService.getRegistries(filter, pageRequest))
                .thenReturn(pageResult);

        String resultJson = mockMvc
                .perform(get("/api/v1/registries/")
                .param("statuses", "DELETED")
                .param("usreou", filter.getUsreou()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PageResult<RegistryReadDto> actualResult = objectMapper
                .readValue(resultJson, new TypeReference<PageResult<RegistryReadDto>>() {});

        Assert.assertEquals(pageResult, actualResult);

        Mockito.verify(registryService).getRegistries(filter, pageRequest);
    }

    @Test
    public void testGetRegistriesWithPagingAndSorting() throws Exception {
        RegistryReadDto readDto = createRegistryReadDto();
        RegistryFilter filter = new RegistryFilter();

        int page = 1;
        int size = 15;

        List<RegistryReadDto> registries = new ArrayList<>();
        registries.add(readDto);

        PageResult<RegistryReadDto> pageResult = new PageResult<>();
        pageResult.setData(registries);
        pageResult.setPageSize(size);
        pageResult.setPage(page);
        pageResult.setTotalPages(4);
        pageResult.setTotalElements(100L);

        PageRequest pageRequest = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "usreou"));

        Mockito.when(registryService.getRegistries(filter, pageRequest))
                .thenReturn(pageResult);

        String resultJson = mockMvc
                .perform(get("/api/v1/registries/")
                .param("page", Integer.toString(page))
                .param("size", Integer.toString(size))
                .param("sort", "usreou,desc"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PageResult<RegistryReadDto> actualResult = objectMapper
                .readValue(resultJson, new TypeReference<PageResult<RegistryReadDto>>() {});

        Assert.assertEquals(pageResult, actualResult);

        Mockito.verify(registryService).getRegistries(filter, pageRequest);
    }

    @Test
    public void testGetRegistryWrongId() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException ex = new EntityNotFoundException(Registry.class, wrongId);
        Mockito.when(registryService.getRegistryById(wrongId)).thenThrow(ex);

        String resultJson = mockMvc
                .perform(get("/api/v1/registries/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(resultJson.contains(ex.getMessage()));
    }

    @Test
    public void testCreateRegistryValidationFailed() throws Exception {
        RegistryCreateDto createDto = new RegistryCreateDto();

        String resultJson = mockMvc
                .perform(post("/api/v1/registries/")
                .content(objectMapper.writeValueAsString(createDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        ErrorInfo error = objectMapper.readValue(resultJson, ErrorInfo.class);
        Mockito.verify(registryService, Mockito.never()).createRegistry(
                ArgumentMatchers.any());
    }

    private RegistryReadDto createRegistryReadDto() {
        RegistryReadDto dto = new RegistryReadDto();
        dto.setId(UUID.randomUUID());
        dto.setDateOfIssue(LocalDate.of(2000, 10, 20));
        dto.setUsreou("123456465");
        dto.setComment("some text");
        dto.setStatus(RegistryStatus.ACTIVE);
        dto.setQuantity(20.0);
        dto.setNominalValue(10.0);
        dto.setTotalValue(200.0);
        return dto;
    }
}
