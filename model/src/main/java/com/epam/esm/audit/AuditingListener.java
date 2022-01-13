package com.epam.esm.audit;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.Map;

/**
 * Class {@code AuditingListener} is designed for auditing database tables.
 *
 * @param <T> the type parameter
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
public abstract class AuditingListener<T> {
    protected static final String ID_COLUMN = "id";
    protected static final String OPERATION_COLUMN = "operation";
    protected static final String CREATE_OPERATION = "CREATE";
    protected static final String UPDATE_OPERATION = "UPDATE";
    protected static final String DELETE_OPERATION = "DELETE";
    protected static final String AUDIT_TIME = "aud_time";

    protected final SimpleJdbcInsert jdbcInsert;

    protected AuditingListener(JdbcTemplate jdbcTemplate, String auditTableName) {
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(auditTableName)
                .usingGeneratedKeyColumns(ID_COLUMN);
    }

    protected abstract Map<String, Object> getInsertArguments(String operation, T entity);

    protected void auditOperation(String operation, T entity) {
        Map<String, Object> args = getInsertArguments(operation, entity);
        jdbcInsert.execute(args);
    }
}
