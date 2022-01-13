package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.CustomGiftCertificateDao;
import com.epam.esm.dao.CustomTagDao;
import com.epam.esm.dao.creator.QueryCreator;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Class {@code CustCustomTagDaoImpl} is implementation of interface {@link CustomTagDao}
 * and intended to work with {@link com.epam.esm.entity.Tag} table.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
@Repository
@Transactional
public class CustomTagDaoImpl extends AbstractDao<Tag> implements CustomTagDao {
    private final QueryCreator<Tag> queryCreator;

    private static final String DELETE_GIFT_CERTIFICATES_HAS_TAGS_QUERY = "DELETE FROM gift_certificates_has_tags "
            + "WHERE tag_id = :tag_id_fk";

    private static final String FIND_MOST_WIDELY_USED_TAG_OF_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS_QUERY = "SELECT t FROM "
            + "GiftCertificate g INNER JOIN g.tags t WHERE g.id IN (SELECT o.giftCertificate.id FROM Order o "
            + "WHERE o.user.id IN (SELECT o.user.id FROM Order o GROUP BY o.user.id ORDER BY SUM(o.price) DESC)) "
            + "GROUP BY t.id ORDER BY COUNT(t.id) DESC";

    @Autowired
    public CustomTagDaoImpl(QueryCreator<Tag> queryCreator) {
        this.queryCreator = queryCreator;
    }

    @Override
    protected QueryCreator<Tag> getQueryCreator() {
        return queryCreator;
    }

    @Override
    public void deleteGiftCertificateHasTag(long id) {
        entityManager.createNativeQuery(DELETE_GIFT_CERTIFICATES_HAS_TAGS_QUERY)
                .setParameter("tag_id_fk", id)
                .executeUpdate();
    }

    @Override
    public Optional<Tag> findMostPopularTagOfUserWithHighestCostOfAllOrders() {
        return entityManager.createQuery(FIND_MOST_WIDELY_USED_TAG_OF_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS_QUERY, Tag.class)
                .setMaxResults(1)
                .getResultList().stream()
                .findFirst();
    }
}
