package org.veritasopher.boostauth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.veritasopher.boostauth.config.GlobalKey;
import org.veritasopher.boostauth.core.dictionary.ErrorCode;
import org.veritasopher.boostauth.core.dictionary.IdentityConst;
import org.veritasopher.boostauth.core.dictionary.TokenConst;
import org.veritasopher.boostauth.core.exception.Assert;
import org.veritasopher.boostauth.core.response.Response;
import org.veritasopher.boostauth.model.Identity;
import org.veritasopher.boostauth.model.Token;
import org.veritasopher.boostauth.model.vo.AuthLogin;
import org.veritasopher.boostauth.model.vo.AuthLogout;
import org.veritasopher.boostauth.model.vo.AuthPreregister;
import org.veritasopher.boostauth.model.vo.AuthRegister;
import org.veritasopher.boostauth.service.IdentityService;
import org.veritasopher.boostauth.service.TokenService;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Authentication Controller
 *
 * @author Yepeng Ding
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private IdentityService identityService;

    @Resource
    private TokenService tokenService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    /**
     * Login a user
     *
     * @param authLogin login information
     * @return message, token
     */
    @PostMapping("/login")
    public Response<String> login(@RequestBody AuthLogin authLogin) {
        Identity identity = identityService.findByUsernameAndSource(authLogin.getUsername(), authLogin.getSource());
        Assert.notNull(identity, ErrorCode.USERNAME_NOT_EXIST, "Username does not exist.");
        Assert.isTrue(identity.getStatus() == IdentityConst.NORMAL, ErrorCode.ABNORMAL, "Account is not normal.");
        Assert.isTrue(bCryptPasswordEncoder.matches(authLogin.getPassword(), identity.getPassword()), ErrorCode.PASSWORD_WRONG, "Wrong password.");

        // Generate and set token
        Token token = identity.getToken();

        Algorithm algorithm = Algorithm.HMAC512(GlobalKey.JWT_SIGNING_KEY);
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.setTime(new Date());
        // Token expiry date (2 hours at default)
        calendar.add(Calendar.HOUR, 2);
//        calendar.add(Calendar.MINUTE, 1);
        Date expiryDate = calendar.getTime();
        token.setIssuingDate(now);
        token.setExpiryDate(expiryDate);
        token.setContent("Bearer " + JWT.create()
                .withIssuer(GlobalKey.ISSUER)
                .withSubject(identity.getUuid())
                .withExpiresAt(expiryDate)
                .sign(algorithm));
        token.setStatus(TokenConst.NORMAL);
        tokenService.update(token);
        return Response.success("Login successfully.", token.getContent());
    }

    /**
     * Preregister a user
     * For cases where the source system needs to register other services
     * Preregistration must be before the formal registration
     *
     * @param authPreregister preregistration information
     * @return message, uuid
     */
    @PostMapping("/preregister")
    public Response<String> preRegister(@RequestBody AuthPreregister authPreregister) {
        Identity existIdentity = identityService.findByUsername(authPreregister.getUsername());

        // Whether is preregistered.
        Assert.isTrue(existIdentity == null || existIdentity.getStatus() != IdentityConst.PREREGISTER, ErrorCode.USERNAME_EXIST, "Username exists.");

        Identity identity = new Identity();
        identity.setUuid(UUID.randomUUID().toString());
        identity.setUsername(authPreregister.getUsername());
        identity.setPassword(bCryptPasswordEncoder.encode(authPreregister.getPassword()));
        identity.setSource(authPreregister.getSource());
        identity.setStatus(IdentityConst.PREREGISTER);

        Token token = new Token();
        token.setStatus(TokenConst.INVALID);
        tokenService.create(token);

        identity.setToken(token);
        identityService.update(identity);
        return Response.success("Preregister successfully.", identity.getUuid());
    }

    /**
     * Register a user (formal registration)
     * Formal registration must be after the preregistration
     *
     * @param authRegister register information
     * @return message, uuid
     */
    @PostMapping("/register")
    public Response<String> register(@RequestBody AuthRegister authRegister) {
        Identity identity = identityService.findByUuid(authRegister.getUuid());
        Assert.notNull(identity, "Register failed because UUID is missing.");
        identity.setStatus(IdentityConst.NORMAL);
        identityService.update(identity);
        return Response.success("Register successfully.", identity.getUuid());
    }

    /**
     * Logout a user
     *
     * @param authLogout logout information
     * @return message
     */
    @PostMapping("/logout")
    public Response<String> logout(@RequestBody AuthLogout authLogout) {
        Identity identity = identityService.findByUuid(authLogout.getUuid());
        Assert.notNull(identity, ErrorCode.USERNAME_NOT_EXIST, "Username does not exist.");
        Token token = identity.getToken();
        token.setStatus(TokenConst.INVALID);
        tokenService.update(token);
        return Response.success("Logout successfully.");
    }
}
