package com.movienchill.mediaservice.service;

import java.util.List;

/**
 * Generic interface containing methods that all services need to implements
 * @param <T> The entity
 * @param <D> DTO of the entity
 */
public interface IGenericService<T, D> {
    /**
     * Method to find all entities in database
     * @return A List of DTO of the entity
     */
    List<D> findAll();

    /**
     * Method to find an entity in database in function of his id
     * @param id the id of the entity
     * @return the DTO
     */
    D findById(Long id);

    /**
     * Method for saving an entity in database
     * @param entity the entity
     * @return the entity saved
     */
    D save(T entity);

    /**
     * Method for deleting an entity in database
     * @param entity the entity
     */
    void delete(T entity);
}
