package org.veritasopher.boostauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.veritasopher.boostauth.config.property.BoostAuthConfig;
import org.veritasopher.boostauth.core.provider.auth.AdminAuthenticationProvider;
import org.veritasopher.boostauth.core.provider.auth.IdentityAuthenticationProvider;

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

    @Resource
    private AdminAuthenticationProvider adminAuthenticationProvider;

    @Resource
    private IdentityAuthenticationProvider identityAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        if (boostAuthConfig.isProduction()) {
            security
                    .authorizeRequests()
                    .antMatchers("/swagger-ui/**", "/v3/**", "/admin/**")
                    .hasAuthority("ADMIN")
                    .antMatchers("/basic/**")
                    .hasAuthority("USER")
                    .and()
                    .httpBasic()
                    .and()
                    .csrf().disable();
        } else {
            security
                    .authorizeRequests()
                    .antMatchers("/admin/**")
                    .hasAuthority("ADMIN")
                    .antMatchers("/basic/**")
                    .hasAuthority("USER")
                    .and()
                    .httpBasic()
                    .and()
                    .csrf().disable();
        }
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(adminAuthenticationProvider)
                .authenticationProvider(identityAuthenticationProvider);
    }
}