package com.movienchill.mediaservice.service;

import java.util.List;

/**
 * Generic interface containing methods that all services need to implement
 *
 * @param <T> The entity
 * @param <D> DTO of the entity
 */
public interface IGenericService<T, D> {
    /**
     * Method to find all entities in database.
     * The method will map the entity and return a list of DTO
     *
     * @return A List of DTO of the entity
     */
    List<D> findAll();

    /**
     * Method to find an entity in database in function of his id
     * The method will map the entity to DTO
     *
     * @param id the id of the entity to find
     * @return the DTO of the entity
     */
    D findById(Long id);

    /**
     * Method to create an entity from DTO
     * 
     * @param entityDto The DTO of the entity
     * @return true if success else false
     */
    boolean create(D entityDto);

    /**
     * Method for saving an entity in database
     *
     * @param entity the entity
     */
    void save(T entity);

    /**
     * Method for deleting an entity in database
     *
     * @param entity the entity
     */
    void delete(Long id);
}
