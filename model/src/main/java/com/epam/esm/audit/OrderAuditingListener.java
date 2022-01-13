package com.epam.esm.audit;

import com.epam.esm.entity.Order;
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
 * and intended to work with {@link com.epam.esm.entity.Order} table.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
@Component
public class OrderAuditingListener extends AuditingListener<Order> {
    private static final String AUDIT_TABLE_NAME = "orders_aud";
    private static final String ORDER_ID = "order_id";
    private static final String ORDER_PRICE = "order_price";
    private static final String ORDER_PURCHASE_TIME = "order_purchase_time";
    private static final String ORDER_USER_ID = "order_user_id";
    private static final String ORDER_GIFT_CERTIFICATE_ID = "order_gift_certificate_id";

    @Autowired
    public OrderAuditingListener(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, AUDIT_TABLE_NAME);
    }

    @PostPersist
    public void onPersist(Order order) {
        auditOperation(CREATE_OPERATION, order);
    }

    @PostUpdate
    public void onUpdate(Order order) {
        auditOperation(UPDATE_OPERATION, order);
    }

    @PreRemove
    public void onDelete(Order order) {
        auditOperation(DELETE_OPERATION, order);
    }

    @Override
    protected Map<String, Object> getInsertArguments(String operation, Order order) {
        Map<String, Object> args = new HashMap<>();
        args.put(OPERATION_COLUMN, operation);
        args.put(ORDER_ID, order.getId());
        args.put(ORDER_PRICE, order.getPrice());
        args.put(ORDER_PURCHASE_TIME, order.getPurchaseTime());
        args.put(ORDER_USER_ID, order.getUser().getId());
        args.put(ORDER_GIFT_CERTIFICATE_ID, order.getGiftCertificate().getId());
        args.put(AUDIT_TIME, now());
        return args;
    }
}
