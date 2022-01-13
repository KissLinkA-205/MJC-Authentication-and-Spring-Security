package com.epam.esm.audit;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDateTime.now;

/**
 * Class {@code AuditingListener} is designed for auditing database tables
 * and intended to work with {@link com.epam.esm.entity.GiftCertificate} table.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
@Component
public class GiftCertificateAuditingListener extends AuditingListener<GiftCertificate> {
    private static final String AUDIT_TABLE_NAME = "gift_certificates_aud";
    private static final String GIFT_CERTIFICATE_ID = "gift_certificate_id";
    private static final String GIFT_CERTIFICATE_NAME = "gift_certificate_name";
    private static final String GIFT_CERTIFICATE_DESCRIPTION = "gift_certificate_description";
    private static final String GIFT_CERTIFICATE_PRICE = "gift_certificate_price";
    private static final String GIFT_CERTIFICATE_DURATION = "gift_certificate_duration";
    private static final String GIFT_CERTIFICATE_CREATE_DATE = "gift_certificate_create_date";
    private static final String GIFT_CERTIFICATE_LAST_UPDATE_DATE = "gift_certificate_last_update_date";

    @Autowired
    public GiftCertificateAuditingListener(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, AUDIT_TABLE_NAME);
    }

    @PostPersist
    public void onPersist(GiftCertificate giftCertificate) {
        auditOperation(CREATE_OPERATION, giftCertificate);
    }

    @PostUpdate
    public void onUpdate(GiftCertificate giftCertificate) {
        auditOperation(UPDATE_OPERATION, giftCertificate);
    }

    @PreRemove
    public void onDelete(GiftCertificate giftCertificate) {
        auditOperation(DELETE_OPERATION, giftCertificate);
    }

    @Override
    protected Map<String, Object> getInsertArguments(String operation, GiftCertificate giftCertificate) {
        Map<String, Object> args = new HashMap<>();
        args.put(OPERATION_COLUMN, operation);
        args.put(GIFT_CERTIFICATE_ID, giftCertificate.getId());
        args.put(GIFT_CERTIFICATE_NAME, giftCertificate.getName());
        args.put(GIFT_CERTIFICATE_DESCRIPTION, giftCertificate.getDescription());
        args.put(GIFT_CERTIFICATE_PRICE, giftCertificate.getPrice());
        args.put(GIFT_CERTIFICATE_DURATION, giftCertificate.getDuration());
        args.put(GIFT_CERTIFICATE_CREATE_DATE, giftCertificate.getCreateDate());
        args.put(GIFT_CERTIFICATE_LAST_UPDATE_DATE, giftCertificate.getLastUpdateDate());
        args.put(AUDIT_TIME, now());
        return args;
    }
}
