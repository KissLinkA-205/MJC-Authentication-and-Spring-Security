package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class {@code UserCredentialDto} represents user credentials.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialDto {

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String token;
}
