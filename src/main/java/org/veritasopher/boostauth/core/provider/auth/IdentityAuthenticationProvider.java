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
import org.veritasopher.boostauth.core.dictionary.GroupStatus;
import org.veritasopher.boostauth.core.exception.Assert;
import org.veritasopher.boostauth.model.Identity;
import org.veritasopher.boostauth.service.IdentityService;
import org.veritasopher.boostauth.utils.CryptoUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Identity Basic Access Authentication Provider
 *
 * @author Yepeng Ding
 */
@Component
public class IdentityAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(IdentityAuthenticationProvider.class);

    @Resource
    private HttpServletRequest request;

    @Resource
    private IdentityService identityService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Skip non-basic requests
        if (!request.getRequestURI().startsWith("/basic/")) {
            return null;
        }

        String username = authentication.getName();
        String credentials = authentication.getCredentials().toString();

        Assert.isTrue(credentials.matches(".+:.+"), () -> {
            logger.error(String.format("Credentials of username (%s) do not contain source.", username));
            throw new BadCredentialsException("Credentials do not contain source.");
        });

        String[] credentialsSplit = credentials.split(":");

        String source = credentialsSplit[0];
        String password = credentialsSplit[1];


        // Check user existence
        Identity identity = identityService.getNormalByUsernameAndSource(username, source).orElseThrow(() -> {
            logger.error(String.format("Username (%s) does not exist.", username));
            throw new BadCredentialsException("Username does not exist.");
        });

        if (CryptoUtils.matchByBCrypt(password, identity.getPassword())) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("USER"));

            // Set identity
            request.setAttribute("identity", identity);
            return new UsernamePasswordAuthenticationToken(
                    username, password, authorities);
        } else {
            logger.error(String.format("Username (%s) has wrong password.", username));
            throw new BadCredentialsException("Username or password is wrong.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
