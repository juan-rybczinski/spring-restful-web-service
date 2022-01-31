package com.example.restfulwebservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http?.let {
            it.authorizeRequests().antMatchers("/h2-console/**").permitAll()
            it.csrf().disable()
            it.headers().frameOptions().disable()
        }
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.inMemoryAuthentication()?.withUser("juan")?.password("{noop}test1234#")?.roles("USER")
    }
}