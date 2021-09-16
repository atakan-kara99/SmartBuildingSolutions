package com.lms2ue1.sbsweb.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lms2ue1.sbsweb.service.DBUserDetailsService;

/**
 * Security configuration for everything related to our REST-API used for communication by the app with the server.
 * @author Luca Anthony Schwarz (sunfl0w)
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class RestSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    DBUserDetailsService userDetailsService;

    @Autowired
    JWTRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // All request to /api/** are related to the REST.API and should need authentication via jwt except /api/auth as this is used for getting a valid jwt
        http.antMatcher("/api/**").authorizeRequests().antMatchers("/api/auth").permitAll().anyRequest().authenticated().and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().httpBasic();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // Definition of the AuthenticationManager. Important as this is not defined by default anymore
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }
}
