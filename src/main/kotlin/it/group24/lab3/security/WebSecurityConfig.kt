package it.group24.lab3.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
@Configuration
class WebSecurityConfig(): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .formLogin().disable()
            .csrf().disable()
    }
}