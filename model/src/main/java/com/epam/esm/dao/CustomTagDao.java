package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.Optional;

/**
 * Interface {@code CustomTagDao} describes abstract behavior for working with
 * {@link com.epam.esm.entity.Tag} in database using custom methods.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
public interface CustomTagDao extends CRDDao<Tag> {

    /**
     * Method for deleting links between gift certificates and tags.
     *
     * @param id ID of tag by which the deletion will be
     */
    void deleteGiftCertificateHasTag(long id);

    /**
     * Method for finding the most popular tag of user with the highest cost of all orders in database.
     *
     * @return Optional of found tag
     */
    Optional<Tag> findMostPopularTagOfUserWithHighestCostOfAllOrders();
}
