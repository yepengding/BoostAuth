package org.veritasopher.boostauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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

    @Autowired
    private AdminAuthenticationProvider authProvider;

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        if (boostAuthConfig.isProduction()) {
            security
                    .authorizeRequests()
                    .antMatchers("/swagger-ui/**", "/v3/**", "/admin/**")
                    .authenticated()
                    .antMatchers("/**").permitAll()
                    .and().httpBasic()
                    .and()
                    .csrf().disable();
        } else {
            security
                    .authorizeRequests()
                    .antMatchers("/admin/**")
                    .authenticated()
                    .and()
                    .httpBasic()
                    .and()
                    .csrf().disable();
        }
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }
}