package org.veritasopher.boostauth.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.veritasopher.boostauth.config.property.BoostAuthConfig;
import org.veritasopher.boostauth.core.exception.type.AuthorizationException;
import org.veritasopher.boostauth.model.Admin;
import org.veritasopher.boostauth.service.AdminService;
import org.veritasopher.boostauth.utils.CryptoUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Admin Authentication Provider
 *
 * @author Yepeng Ding
 */
@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private BoostAuthConfig boostAuthConfig;

    @Resource
    private AdminService adminService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Set super admin if not existing
        Admin admin = adminService.getNormalByUsername(name).orElse(new Admin(name, password));
        if (boostAuthConfig.getAdminUsername().equals(admin.getUsername()) && boostAuthConfig.getAdminPassword().equals(admin.getPassword()) ||
                CryptoUtils.matchByBCrypt(password, admin.getPassword())) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            return new UsernamePasswordAuthenticationToken(
                    name, password, authorities);
        } else {
            throw new AuthorizationException("Username or password is wrong.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
