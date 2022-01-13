package com.epam.esm.exception;

import java.util.List;

/**
 * Class {@code ErrorResponse} represents objects that will be returned as a response when an error is generated.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
public class ErrorResponse {
    private final String errorCode;
    private final List<String> errorMessages;

    public ErrorResponse(String errorCode, List<String> errorMessages) {
        this.errorCode = errorCode;
        this.errorMessages = errorMessages;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
