package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionResult;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.epam.esm.entity.Role.USER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderValidatorTest {
    private static final User INCORRECT_USER = new User(-1, "email.com", "123", USER, null);
    private static final User CORRECT_USER = new User(1, "email@email.com", "12345678", USER, "name");

    private static final GiftCertificate INCORRECT_GIFT_CERTIFICATE = new GiftCertificate(-5, null, null, new BigDecimal("12"), 2,
            LocalDateTime.parse("2018-08-29T06:12:15"), LocalDateTime.parse("2018-08-29T06:12:15"), null, false);
    private static final GiftCertificate CORRECT_GIFT_CERTIFICATE = new GiftCertificate(5, null, null, new BigDecimal("12"), 2,
            LocalDateTime.parse("2018-08-29T06:12:15"), LocalDateTime.parse("2018-08-29T06:12:15"), null, false);

    private static final Order INCORRECT_ORDER = new Order(1, new BigDecimal("10.5"),
            LocalDateTime.parse("2020-08-29T06:12:15.156"), INCORRECT_USER, INCORRECT_GIFT_CERTIFICATE);

    private static final Order CORRECT_ORDER = new Order(0, new BigDecimal("10.5"),
            LocalDateTime.parse("2020-08-29T06:12:15.156"), CORRECT_USER, CORRECT_GIFT_CERTIFICATE);

    @Test
    void testValidate_incorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        OrderValidator.validate(INCORRECT_ORDER, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testValidate_correctData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        OrderValidator.validate(CORRECT_ORDER, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testValidateUserId_incorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        OrderValidator.validateUserId(INCORRECT_USER.getId(), exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testValidateUserId_correctData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        OrderValidator.validateUserId(CORRECT_USER.getId(), exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testValidateGiftCertificateId_incorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        OrderValidator.validateGiftCertificateId(INCORRECT_GIFT_CERTIFICATE.getId(), exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testValidateGiftCertificateId_correctData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        OrderValidator.validateGiftCertificateId(CORRECT_GIFT_CERTIFICATE.getId(), exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }
}