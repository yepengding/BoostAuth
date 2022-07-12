package org.veritasopher.boostauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.veritasopher.boostauth.config.property.BoostAuthConfig;

import javax.annotation.Resource;

/**
 * Security Configuration
 *
 * @author Yepeng Ding
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private BoostAuthConfig boostAuthConfig;

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        if (boostAuthConfig.isProduction()) {
            security
                    .authorizeRequests()
                    .antMatchers("/swagger-ui/**", "/v3/**", "/admin/**")
                    .authenticated()
                    .antMatchers("/**").permitAll()
                    .and().httpBasic()
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .and()
                    .csrf().disable();
        } else {
            security
                    .authorizeRequests()
                    .antMatchers("/admin/**")
                    .authenticated()
                    .and()
                    .httpBasic()
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .and()
                    .csrf().disable();
        }
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        BasicAuthenticationEntryPoint entryPoint =
                new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("admin realm");
        return entryPoint;
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(boostAuthConfig.getAdminUsername()).password(new BCryptPasswordEncoder().encode(boostAuthConfig.getAdminPassword()))
                .authorities("ROLE_ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}