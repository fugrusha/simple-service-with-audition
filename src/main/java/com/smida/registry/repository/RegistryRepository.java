package com.smida.registry.repository;

import com.smida.registry.domain.Registry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegistryRepository extends CrudRepository<Registry, UUID> {
}
