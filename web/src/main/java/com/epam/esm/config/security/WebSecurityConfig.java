package com.epam.esm.config.security;

import com.epam.esm.exception.AccessDeniedHandlerEntryPoint;
import com.epam.esm.exception.AuthenticationHandlerEntryPoint;
import com.epam.esm.jwt.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

/**
 * Class {@code WebSecurityConfig} contains spring security configuration.
 *
 * @author Anzhalika Dziarkach
 * @version 1.0
 * @see WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JWTFilter jwtFilter;
    private final AuthenticationHandlerEntryPoint authenticationHandlerEntryPoint;
    private final AccessDeniedHandlerEntryPoint accessDeniedHandlerEntryPoint;

    private static final String ADMIN = "ADMIN";

    @Autowired
    public WebSecurityConfig(JWTFilter jwtFilter, AuthenticationHandlerEntryPoint authenticationHandlerEntryPoint,
                             AccessDeniedHandlerEntryPoint accessDeniedHandlerEntryPoint) {
        this.jwtFilter = jwtFilter;
        this.authenticationHandlerEntryPoint = authenticationHandlerEntryPoint;
        this.accessDeniedHandlerEntryPoint = accessDeniedHandlerEntryPoint;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                //All
                .antMatchers(GET, "/certificates/**").permitAll()
                .antMatchers(POST, "/register", "/auth").permitAll()
                //User
                .antMatchers(POST, "/orders").fullyAuthenticated()
                .antMatchers(GET, "/tags/**", "/users/**").fullyAuthenticated()
                //Admin
                .anyRequest().hasRole(ADMIN)
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandlerEntryPoint).authenticationEntryPoint(authenticationHandlerEntryPoint)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
