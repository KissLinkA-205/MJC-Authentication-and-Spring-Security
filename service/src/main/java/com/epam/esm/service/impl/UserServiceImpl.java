package com.epam.esm.service.impl;

import com.epam.esm.dao.CRDDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Class {@code UserServiceImpl} is implementation of interface {@link UserService}
 * and intended to work with {@link User} objects.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncode;

    @Autowired
    public UserServiceImpl(JpaRepository<User, Long> dao, UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(dao);
        this.userDao = userDao;
        this.bCryptPasswordEncode = bCryptPasswordEncoder;
    }

    @Override
    public User update(long id, User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User insert(User user) {
        ExceptionResult exceptionResult = new ExceptionResult();
        UserValidator.validate(user, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        user.setPassword(bCryptPasswordEncode.encode(user.getPassword()));

        String userEmail = user.getEmail();
        boolean isUserExist = userDao.findByEmail(userEmail).isPresent();
        if (isUserExist) {
            throw new DuplicateEntityException(ExceptionMessageKey.USER_EXIST);
        }
        user.setRole(Role.USER);
        return dao.save(user);
    }

    @Override
    public void removeById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> doFilter(MultiValueMap<String, String> requestParams, int page, int size) {
        throw new UnsupportedOperationException();
    }
}
