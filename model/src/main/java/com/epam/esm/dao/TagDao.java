package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Interface {@code TagDao} describes abstract behavior for working with
 * {@link com.epam.esm.entity.Tag} in database.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
@Repository
@Transactional
public interface TagDao extends JpaRepository<Tag, Long>, CustomTagDao {

    /**
     * Method for getting a tag from a table with a specific name.
     *
     * @param name name of tag to get
     * @return Optional of tag entity
     */
    Optional<Tag> findByName(String name);
}
