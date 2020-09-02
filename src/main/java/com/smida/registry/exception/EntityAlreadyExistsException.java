package com.smida.registry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(Class entityClass, String usreou) {
        super(String.format("Entity %s with usreou=%s already exists",
                        entityClass, usreou));
    }
}
