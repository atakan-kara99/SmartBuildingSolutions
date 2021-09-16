package com.lms2ue1.sbsweb.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lms2ue1.sbsweb.service.DBUserDetailsService;

@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    DBUserDetailsService userDetailsService;

    @Bean
    protected PasswordEncoder getPasswordEncoder() {
	return new BCryptPasswordEncoder();
    }


	// TODO: Sysadmin und Orgadmin abgrenzen
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/h2-console/**").permitAll() // We can't get locked out.
			.antMatchers("/images/**", "/css/**", "/index", "/").permitAll()
			.anyRequest().authenticated()
			.and()
			//.antMatchers("/organisation_*/**")
			.formLogin().loginPage("/login").permitAll()
			.defaultSuccessUrl("/index", true)
			.and().logout().permitAll();
		
		// Comment in to enable H2 console on test server (not recommended for release version!)
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }
}
