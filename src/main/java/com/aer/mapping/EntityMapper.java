package com.aer.mapping;

import java.util.List;

public interface EntityMapper<D, E> {

    /**
     * To dto.
     *
     * @param entity the entity
     * @return the dto
     */
    D toDto(E entity);

    /**
     * To entity.
     *
     * @param dto the dto
     * @return the e
     */
    E toEntity(D dto);

    /**
     * To dtos.
     *
     * @param entities the entities
     * @return the list
     */
    List<D> toDtos(List<E> entities);

    /**
     * To entities.
     *
     * @param dtos the dtos
     * @return the list
     */
    List<E> toEntities(List<D> dtos);
}
