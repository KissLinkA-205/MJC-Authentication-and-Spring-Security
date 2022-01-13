package com.epam.esm.audit;

import com.epam.esm.entity.Tag;
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
 * and intended to work with {@link com.epam.esm.entity.Tag} table.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
@Component
public class TagAuditingListener extends AuditingListener<Tag> {
    private static final String AUDIT_TABLE_NAME = "tags_aud";
    private static final String TAG_ID = "tag_id";
    private static final String TAG_NAME = "tag_name";

    @Autowired
    public TagAuditingListener(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, AUDIT_TABLE_NAME);
    }

    @PostPersist
    public void onPersist(Tag tag) {
        auditOperation(CREATE_OPERATION, tag);
    }

    @PostUpdate
    public void onUpdate(Tag tag) {
        auditOperation(UPDATE_OPERATION, tag);
    }

    @PreRemove
    public void onDelete(Tag tag) {
        auditOperation(DELETE_OPERATION, tag);
    }

    @Override
    protected Map<String, Object> getInsertArguments(String operation, Tag tag) {
        Map<String, Object> args = new HashMap<>();
        args.put(OPERATION_COLUMN, operation);
        args.put(TAG_ID, tag.getId());
        args.put(TAG_NAME, tag.getName());
        args.put(AUDIT_TIME, now());
        return args;
    }
}
