package com.epam.esm.validator;

import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionResult;
import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.esm.exception.ExceptionMessageKey.*;

/**
 * Utility class {@code UserValidator} provides methods to validate fields of {@link com.epam.esm.entity.User}.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
@UtilityClass
public class UserValidator {
    private final int MAX_LENGTH_NAME = 100;
    private final int MIN_LENGTH_NAME = 1;
    private final int MIN_LENGTH_PASSWORD = 8;
    private final String EMAIL_PATTERN = "^.+@.+\\..+$";

    /**
     * Validate all fields of user.
     *
     * @param user the user
     * @param er   the object to which the validation results will be written
     */
    public void validate(User user, ExceptionResult er) {
        IdentifiableValidator.validateExistenceOfId(user.getId(), er);
        validateEmail(user.getEmail(), er);
        validatePassword(user.getPassword(), er);
        validateName(user.getName(), er);
    }

    /**
     * Validate user email.
     *
     * @param email the user email
     * @param er    the object to which the validation results will be written
     */
    public void validateEmail(String email, ExceptionResult er) {
        if (email == null) {
            er.addException(BAD_USER_EMAIL, email);
        } else {
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                er.addException(BAD_USER_EMAIL, email);
            }
        }
    }

    /**
     * Validate user password.
     *
     * @param password the user password
     * @param er       the object to which the validation results will be written
     */
    public void validatePassword(String password, ExceptionResult er) {
        if (password == null || password.length() < MIN_LENGTH_PASSWORD) {
            er.addException(BAD_USER_PASSWORD, password);
        }
    }

    private void validateName(String name, ExceptionResult er) {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            er.addException(BAD_USER_NAME, name);
        }
    }
}
