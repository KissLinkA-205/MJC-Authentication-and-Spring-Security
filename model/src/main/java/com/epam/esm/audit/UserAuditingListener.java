package com.epam.esm.audit;

import com.epam.esm.entity.User;
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
 * and intended to work with {@link com.epam.esm.entity.User} table.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
@Component
public class UserAuditingListener extends AuditingListener<User> {
    private static final String AUDIT_TABLE_NAME = "users_aud";
    private static final String USER_ID = "user_id";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_ROLE = "user_role";
    private static final String USER_NAME = "user_name";

    @Autowired
    public UserAuditingListener(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, AUDIT_TABLE_NAME);
    }

    @PostPersist
    public void onPersist(User user) {
        auditOperation(CREATE_OPERATION, user);
    }

    @PostUpdate
    public void onUpdate(User user) {
        auditOperation(UPDATE_OPERATION, user);
    }

    @PreRemove
    public void onDelete(User user) {
        auditOperation(DELETE_OPERATION, user);
    }

    @Override
    protected Map<String, Object> getInsertArguments(String operation, User user) {
        Map<String, Object> args = new HashMap<>();
        args.put(OPERATION_COLUMN, operation);
        args.put(USER_ID, user.getId());
        args.put(USER_EMAIL, user.getEmail());
        args.put(USER_PASSWORD, user.getPassword());
        args.put(USER_ROLE, user.getRole());
        args.put(USER_NAME, user.getName());
        args.put(AUDIT_TIME, now());
        return args;
    }
}
