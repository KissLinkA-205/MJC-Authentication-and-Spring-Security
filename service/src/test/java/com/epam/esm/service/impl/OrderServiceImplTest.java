package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.logic.handler.DateHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.entity.Role.ADMIN;
import static com.epam.esm.entity.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderDao orderDao = Mockito.mock(OrderDao.class);

    @Mock
    private UserDao userDao = Mockito.mock(UserDao.class);

    @Mock
    private GiftCertificateDao giftCertificateDao = Mockito.mock(GiftCertificateDao.class);

    @Mock
    private DateHandler dateHandler = Mockito.mock(DateHandler.class);
    private static final LocalDateTime UPDATED_DATE = LocalDateTime.parse("2018-08-29T06:12:15.156");

    @InjectMocks
    private OrderServiceImpl orderService;

    private static final User USER_1 = new User(1, "email1@email.com",
            "$2a$10$zFdd9cCOS/A6./WuG7etC.Mhn3M.R4u2VFMVlz4cVSiNK6bLaT7S.", ADMIN, "name1");
    private static final User USER_2 = new User(2, "email2@email.com",
            "$2a$10$gwocSNWrBeUOO.0.0./iveTo/uDatwYEWHadx1xMUZ.Do93yS9kc2", USER, "name2");

    private static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1, "giftCertificate1", "description1", new BigDecimal("10.1"),
            1, LocalDateTime.parse("2020-08-29T06:12:15"), LocalDateTime.parse("2020-08-29T06:12:15"), null, false);
    private static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2, "giftCertificate3", "description3", new BigDecimal("30.3"),
            3, LocalDateTime.parse("2019-08-29T06:12:15"), LocalDateTime.parse("2019-08-29T06:12:15"), null, false);

    private static final Order ORDER_1 = new Order(1, new BigDecimal("15.2"), UPDATED_DATE, USER_1, GIFT_CERTIFICATE_1);
    private static final Order ORDER_2 = new Order(2, new BigDecimal("30.4"), UPDATED_DATE, USER_2, GIFT_CERTIFICATE_2);
    private static final Order ORDER_FOR_INSERT = new Order(0, new BigDecimal("15.2"), UPDATED_DATE, USER_1, GIFT_CERTIFICATE_1);

    private static final int PAGE = 0;
    private static final int SIZE = 5;

    @Test
    public void testGetById() {
        when(orderDao.findById(ORDER_1.getId())).thenReturn(Optional.of(ORDER_1));

        Order actual = orderService.getById(ORDER_1.getId());
        Order expected = ORDER_1;

        assertEquals(expected, actual);
    }

    @Test
    public void testInsert() {
        when(orderDao.save(ORDER_FOR_INSERT)).thenReturn(ORDER_FOR_INSERT);
        when(dateHandler.getCurrentDate()).thenReturn(UPDATED_DATE);
        when(userDao.findById(ORDER_FOR_INSERT.getUser().getId())).thenReturn(Optional.of(new User()));
        when(giftCertificateDao.findById(ORDER_FOR_INSERT.getGiftCertificate().getId())).thenReturn(Optional.of(new GiftCertificate()));

        Order actual = orderService.insert(ORDER_FOR_INSERT);
        Order expected = ORDER_FOR_INSERT;

        assertEquals(expected, actual);
    }

    @Test
    public void testGetOrdersByUserId() {
        List<Order> orders = Arrays.asList(ORDER_1, ORDER_2);
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        when(orderDao.findByUserId(ORDER_1.getUser().getId(), pageRequest)).thenReturn(orders);

        List<Order> actual = orderService.getOrdersByUserId(ORDER_1.getUser().getId(), PAGE, SIZE);
        List<Order> expected = orders;

        assertEquals(expected, actual);
    }
}