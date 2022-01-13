package com.epam.esm.controller;

import com.epam.esm.dto.UserCredentialDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.entity.User;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.jwt.JWTProvider;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class {@code AuthenticationController} is an endpoint of the API which allows to perform operations for authorization.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 *
 * @author Anzhalika Dziarkach
 * @since 1.0
 */
@RestController
public class AuthenticationController {
    private final UserService userService;

    private final DtoConverter<User, UserDto> userDtoConverter;
    private final JWTProvider jwtProvider;
    private final HateoasAdder<UserDto> hateoasAdder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(UserService userService, DtoConverter<User, UserDto> userDtoConverter,
                                    HateoasAdder<UserDto> hateoasAdder, JWTProvider jwtProvider,
                                    AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
        this.hateoasAdder = hateoasAdder;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Method for registration new user.
     *
     * @param user user to register
     * @return registered user with hateoas
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public UserDto registerUser(@RequestBody UserDto user) {
        User addedUser = userService.insert(userDtoConverter.convertToEntity(user));

        UserDto userDto = userDtoConverter.convertToDto(addedUser);
        hateoasAdder.addLinks(userDto);
        return userDto;
    }

    /**
     * Method for authorizing an existing user.
     *
     * @param userCredentialDto user credentials
     * @return user credentials with unique token
     */
    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public UserCredentialDto authorizeUser(@RequestBody UserCredentialDto userCredentialDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userCredentialDto.getEmail(), userCredentialDto.getPassword()));
        String token = jwtProvider.generateToken(userCredentialDto.getEmail());
        userCredentialDto.setToken(token);
        return userCredentialDto;
    }
}
