package com.epam.esm.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Interface {@code CRDDao} describes CRD operations for working with database tables.
 *
 * @param <T> the type parameter
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
public interface CRDDao<T> {

    /**
     * Method for getting a list of entities by specific parameters.
     *
     * @param fields   parameters by which the filter will be performed
     * @param pageable pageable object with pagination info (page number, page size)
     * @return List of entity objects
     */
    List<T> findWithFilters(MultiValueMap<String, String> fields, Pageable pageable);
}
