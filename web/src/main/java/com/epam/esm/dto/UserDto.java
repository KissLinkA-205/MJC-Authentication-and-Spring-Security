package com.epam.esm.dto;

import com.epam.esm.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * Class {@code UserDto} represents user entity with hateoas.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends RepresentationModel<UserDto> {

    private long id;
    private String email;
    private String password;
    private Role role;
    private String name;
}
