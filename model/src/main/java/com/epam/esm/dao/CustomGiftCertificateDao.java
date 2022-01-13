package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

/**
 * Interface {@code CustomGiftCertificateDao} describes abstract behavior for working with
 * {@link com.epam.esm.entity.GiftCertificate} in database using custom methods.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
public interface CustomGiftCertificateDao extends CRDDao<GiftCertificate> {

    /**
     * Method for deleting links between gift certificates and tags.
     *
     * @param id ID of gift certificate by which the deletion will be
     */
    void deleteGiftCertificateHasTag(long id);
}
