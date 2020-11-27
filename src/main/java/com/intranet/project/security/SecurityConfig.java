package com.intranet.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{ // autoriseeri päringuid üle http
        http.authorizeRequests()
                .antMatchers("/","/register", "/user/login", "/user/create").permitAll() // juhul kui päring tehakse /home pihta luba sinna kõik ligi
                .anyRequest().permitAll();/*.authenticated();*/ // kõik muud päringud peavad olema kasutaja audenditud

        http.cors();
        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
