package com.epam.esm.dao;

import com.epam.esm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Interface {@code UserDao} describes abstract behavior for working with
 * {@link com.epam.esm.entity.User} in database.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
@Repository
@Transactional
public interface UserDao extends JpaRepository<User, Long> {

    /**
     * Method for getting a user from a table with a specific email.
     *
     * @param email email of user to get
     * @return Optional of user entity
     */
    Optional<User> findByEmail(String email);
}
