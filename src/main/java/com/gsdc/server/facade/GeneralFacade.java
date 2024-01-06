package com.gsdc.server.facade;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class GeneralFacade<E, D> {
    private final Class<E> entityClass;

    private final Class<D> dtoClass;

    private final ModelMapper modelMapper = new ModelMapper();

    public GeneralFacade(final Class<E> eclass, final Class<D> dclass) {
        entityClass = eclass;
        dtoClass = dclass;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public E convertToEntity (final D dto) {
        final E entity = modelMapper.map(dto, entityClass);

        decorateEntity(entity, dto);

        return entity;
    }

    public D convertToDto(final E entity) {
        final D dto = modelMapper.map(entity, dtoClass);

        decorateDto(dto, entity);

        return dto;
    }

    protected void decorateEntity(E entity, D dto) {

    }

    protected void decorateDto(D dto, E entity) {

    }
}