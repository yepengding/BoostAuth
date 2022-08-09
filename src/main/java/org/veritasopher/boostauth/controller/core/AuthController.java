package org.veritasopher.boostauth.controller.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.veritasopher.boostauth.config.GlobalKey;
import org.veritasopher.boostauth.core.dictionary.ErrorCode;
import org.veritasopher.boostauth.core.dictionary.IdentityStatus;
import org.veritasopher.boostauth.core.dictionary.TokenStatus;
import org.veritasopher.boostauth.core.exception.Assert;
import org.veritasopher.boostauth.core.exception.type.AuthenticationException;
import org.veritasopher.boostauth.core.exception.type.AuthorizationException;
import org.veritasopher.boostauth.core.exception.type.BadRequestException;
import org.veritasopher.boostauth.core.response.Response;
import org.veritasopher.boostauth.model.Identity;
import org.veritasopher.boostauth.model.Token;
import org.veritasopher.boostauth.model.vo.authreq.*;
import org.veritasopher.boostauth.service.GroupService;
import org.veritasopher.boostauth.service.IdentityService;
import org.veritasopher.boostauth.service.TokenService;
import org.veritasopher.boostauth.utils.CryptoUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
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
    private GroupService groupService;

    @Resource
    private TokenService tokenService;


    /**
     * Login a user
     *
     * @param authLogin login information
     * @return message, token
     */
    @PostMapping("/login")
    public Response<String> login(@Valid @RequestBody AuthLogin authLogin) {
        Identity identity = identityService.getByUsernameAndSource(authLogin.getUsername(), authLogin.getSource()).orElseThrow(() -> {
            throw new AuthenticationException("Username does not exist.");
        });

        // Identity should be at normal status
        Assert.isTrue(IdentityStatus.NORMAL.isTrue(identity.getStatus()), () -> {
            throw new AuthorizationException("Identity is abnormal.");
        });

        // Group should be at normal status
        Assert.isTrue(groupService.getNormalById(identity.getGroupId()).isPresent(), () -> {
            throw new AuthorizationException("Group is abnormal");
        });

        // Check password
        Assert.isTrue(CryptoUtils.matchByBCrypt(authLogin.getPassword(), identity.getPassword()), () -> {
            throw new AuthorizationException("Username or password is wrong.");
        });

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
        token.setContent("Bearer " + JWT.create().withIssuer(GlobalKey.ISSUER).withSubject(identity.getUuid()).withExpiresAt(expiryDate).sign(algorithm));
        token.setStatus(TokenStatus.NORMAL.getValue());
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
    public Response<String> preregister(@Valid @RequestBody AuthPreregister authPreregister) {
        // Check existence.
        Assert.isTrue(identityService.getByUsernameAndSource(authPreregister.getUsername(), authPreregister.getSource()).isEmpty(), () -> {
            throw new BadRequestException(ErrorCode.EXIST, "Username exists.");
        });

        // Check group existence
        Assert.isTrue(groupService.getNormalById(authPreregister.getGroupId()).isPresent(), () -> {
            throw new BadRequestException("Group does not exist.");
        });

        Identity identity = new Identity();
        identity.setUuid(UUID.randomUUID().toString());
        identity.setUsername(authPreregister.getUsername());
        identity.setPassword(CryptoUtils.encodeByBCrypt(authPreregister.getPassword()));
        identity.setSource(authPreregister.getSource());
        identity.setGroupId(authPreregister.getGroupId());
        identity.setStatus(IdentityStatus.PREREGISTER.getValue());

        Token token = new Token();
        token.setStatus(TokenStatus.INVALID.getValue());
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
    public Response<String> register(@Valid @RequestBody AuthRegister authRegister) {
        Identity identity = identityService.getByUuid(authRegister.getUuid()).orElseThrow(() -> {
            throw new AuthenticationException(ErrorCode.NOT_EXIST, "Register failed because identity does not exist.");
        });

        // Identity should be at preregister status
        Assert.isTrue(IdentityStatus.PREREGISTER.isTrue(identity.getStatus()), () -> {
            throw new AuthorizationException("Identity is abnormal.");
        });

        identity.setStatus(IdentityStatus.NORMAL.getValue());
        identityService.update(identity);
        return Response.success("Register successfully.", identity.getUuid());
    }

    /**
     * Logout a user
     *
     * @param authLogout logout information
     * @return message
     */
    @Operation(security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("/logout")
    public Response<String> logout(@Valid @RequestBody AuthLogout authLogout) {
        Token token = tokenService.getByContent(authLogout.getToken()).orElseThrow(() -> {
            throw new AuthenticationException(ErrorCode.NOT_EXIST, "Token does not exist.");
        });

        token.setContent(null);
        token.setStatus(TokenStatus.INVALID.getValue());
        tokenService.update(token);
        return Response.success("Logout successfully.");
    }

    /**
     * Reset password
     *
     * @param authResetPwd reset password information
     * @return message
     */
    @PostMapping("/reset/password")
    public Response<String> resetPwd(@Valid @RequestBody AuthResetPwd authResetPwd) {
        Identity identity = identityService.getByUuid(authResetPwd.getUuid()).orElseThrow(() -> {
            throw new AuthenticationException(ErrorCode.NOT_EXIST, "Identity does not exist.");
        });

        // Identity should be at normal status
        Assert.isTrue(IdentityStatus.NORMAL.isTrue(identity.getStatus()), () -> {
            throw new AuthorizationException("Identity is abnormal.");
        });

        // Check password
        Assert.isTrue(CryptoUtils.matchByBCrypt(authResetPwd.getOldPassword(), identity.getPassword()), () -> {
            throw new AuthorizationException("Wrong password.");
        });

        // Update password
        identity.setPassword(CryptoUtils.encodeByBCrypt(authResetPwd.getNewPassword()));

        identityService.update(identity);

        return Response.success("Reset password successfully.");
    }

}