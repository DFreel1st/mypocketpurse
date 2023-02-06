package com.project.mypocketpurse.service;

import com.project.mypocketpurse.exception.MyDeleteException;

import java.util.List;

public abstract class GenericService<T, N> {
    public abstract T update(T object);

    public abstract T updateFromDTO(N object, Long id);

    public abstract T createFromDTO(N newDtoObject);

    public abstract T createFromEntity(T newObject);

    public abstract void delete(final Long objectId) throws MyDeleteException;

    public abstract T getOne(final Long objectId);

    public abstract List<T> listAll();
}