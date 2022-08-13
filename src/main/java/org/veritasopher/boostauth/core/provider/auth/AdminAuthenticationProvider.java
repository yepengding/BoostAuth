package org.veritasopher.boostauth.core.provider.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.veritasopher.boostauth.config.property.BoostAuthConfig;
import org.veritasopher.boostauth.model.Admin;
import org.veritasopher.boostauth.service.AdminService;
import org.veritasopher.boostauth.utils.CryptoUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Admin Authentication Provider
 *
 * @author Yepeng Ding
 */
@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(AdminAuthenticationProvider.class);

    @Resource
    private HttpServletRequest request;

    @Resource
    private BoostAuthConfig boostAuthConfig;

    @Resource
    private AdminService adminService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Skip non-admin requests
        if (!request.getRequestURI().startsWith("/admin/")) {
            return null;
        }

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Set super admin if not existing
        Admin admin = adminService.getNormalByUsername(username).orElse(new Admin(username, password));

        if (boostAuthConfig.getAdminUsername().equals(admin.getUsername()) && boostAuthConfig.getAdminPassword().equals(admin.getPassword()) ||
                CryptoUtils.matchByBCrypt(password, admin.getPassword())) {
            // If is a super admin or admin, then grant "ADMIN" role
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            return new UsernamePasswordAuthenticationToken(
                    username, password, authorities);
        } else {
            logger.error(String.format("Admin username (%s) has wrong password.", username));
            throw new BadCredentialsException("Username or password is wrong.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
