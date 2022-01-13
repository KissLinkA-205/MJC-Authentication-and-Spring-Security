package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.CustomGiftCertificateDao;
import com.epam.esm.dao.creator.QueryCreator;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class {@code CustomGiftCertificateDaoImpl} is implementation of interface {@link CustomGiftCertificateDao}
 * and intended to work with {@link com.epam.esm.entity.GiftCertificate} table.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
@Repository
@Transactional
public class CustomGiftCertificateDaoImpl extends AbstractDao<GiftCertificate> implements CustomGiftCertificateDao {
    private final QueryCreator<GiftCertificate> queryCreator;

    private static final String DELETE_GIFT_CERTIFICATES_HAS_TAGS_QUERY = "DELETE FROM gift_certificates_has_tags " +
            "WHERE gift_certificate_id = :gift_certificate_id_fk";

    @Autowired
    public CustomGiftCertificateDaoImpl(QueryCreator<GiftCertificate> queryCreator) {
        this.queryCreator = queryCreator;
    }

    @Override
    protected QueryCreator<GiftCertificate> getQueryCreator() {
        return queryCreator;
    }

    @Override
    public void deleteGiftCertificateHasTag(long id) {
        entityManager.createNativeQuery(DELETE_GIFT_CERTIFICATES_HAS_TAGS_QUERY)
                .setParameter("gift_certificate_id_fk", id)
                .executeUpdate();
    }
}
