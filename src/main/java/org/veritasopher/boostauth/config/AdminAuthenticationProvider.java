package org.veritasopher.boostauth.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.veritasopher.boostauth.config.property.BoostAuthConfig;
import org.veritasopher.boostauth.model.Admin;
import org.veritasopher.boostauth.service.AdminService;
import org.veritasopher.boostauth.utils.CryptoUtils;

import javax.annotation.Resource;
import java.util.ArrayList;

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
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Set super admin if not existing
        Admin admin = adminService.getNormalByUsername(name).orElse(new Admin(name, password));
        if (boostAuthConfig.getAdminUsername().equals(admin.getUsername()) && boostAuthConfig.getAdminPassword().equals(admin.getPassword()) ||
                CryptoUtils.matchByBCrypt(password, admin.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    name, password, new ArrayList<>());
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
