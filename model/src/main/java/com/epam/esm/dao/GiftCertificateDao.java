package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Interface {@code GiftCertificateDao} describes abstract behavior for working with
 * {@link com.epam.esm.entity.GiftCertificate} in database.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
@Repository
@Transactional
public interface GiftCertificateDao extends JpaRepository<GiftCertificate, Long>, CustomGiftCertificateDao {

    /**
     * Method for getting a gift certificate from a table with a specific name.
     *
     * @param name name of gift certificate to get
     * @return Optional of gift certificate entity
     */
    Optional<GiftCertificate> findByName(String name);

    /**
     * Method for setting a deleted flag of gift certificate from a table by ID.
     *
     * @param isDeleted deleted flag of gift certificate to set
     * @param id ID of gift certificate
     */
    @Modifying
    @Query("UPDATE GiftCertificate gc SET gc.deleted = ?1 WHERE gc.id = ?2")
    void setDeletedById(boolean isDeleted, long id);
}
