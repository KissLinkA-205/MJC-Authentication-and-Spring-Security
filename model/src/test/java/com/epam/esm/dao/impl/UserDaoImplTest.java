package com.epam.esm.dao.impl;

import com.epam.esm.config.DaoConfig;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.entity.Role.ADMIN;
import static com.epam.esm.entity.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DaoConfig.class)
class UserDaoImplTest {
    private final UserDao userDao;

    private static final User USER_1 = new User(1, "email1@email.com",
            "$2a$10$zFdd9cCOS/A6./WuG7etC.Mhn3M.R4u2VFMVlz4cVSiNK6bLaT7S.", ADMIN, "name1");
    private static final User USER_2 = new User(2, "email2@email.com",
            "$2a$10$gwocSNWrBeUOO.0.0./iveTo/uDatwYEWHadx1xMUZ.Do93yS9kc2", USER, "name2");

    private static final Pageable pageRequest = PageRequest.of(0, 5);

    @Autowired
    public UserDaoImplTest(UserDao userDao) {
        this.userDao = userDao;
    }

    @Test
    public void testFindAll() {
        List<User> actual = userDao.findAll(pageRequest).toList();
        List<User> expected = Arrays.asList(USER_1, USER_2);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindById() {
        Optional<User> actual = userDao.findById(USER_2.getId());
        Optional<User> expected = Optional.of(USER_2);

        assertEquals(expected, actual);
    }
}