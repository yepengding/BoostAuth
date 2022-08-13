package org.veritasopher.boostauth.controller.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.veritasopher.boostauth.config.GlobalKey;
import org.veritasopher.boostauth.core.dictionary.GroupStatus;
import org.veritasopher.boostauth.core.dictionary.IdentityStatus;
import org.veritasopher.boostauth.core.dictionary.TokenStatus;
import org.veritasopher.boostauth.core.exception.Assert;
import org.veritasopher.boostauth.core.exception.type.AuthorizationException;
import org.veritasopher.boostauth.core.exception.type.BadRequestException;
import org.veritasopher.boostauth.core.response.Response;
import org.veritasopher.boostauth.model.Identity;
import org.veritasopher.boostauth.model.vo.authreq.AuthVerify;
import org.veritasopher.boostauth.service.GroupService;
import org.veritasopher.boostauth.service.IdentityService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

/**
 * Verification Controller
 *
 * @author Yepeng Ding
 */
@Tag(name = "Token Authentication")
@RestController
@RequestMapping("/token")
public class VerifyController {

    @Resource
    private IdentityService identityService;

    /**
     * Verify a token
     *
     * @param authVerify verification information
     * @return message, uuid
     */
    @PostMapping("/verify")
    public Response<String> verifyToken(@Valid @RequestBody AuthVerify authVerify) {
        String token = authVerify.getToken().replaceFirst("Bearer ", "");

        // Basic validation
        Assert.notNull(token, () -> {
            throw new BadRequestException("No token information.");
        });
        Assert.isTrue(!"".equals(token), () -> {
            throw new BadRequestException("Token is empty.");
        });

        // Parse JWT
        Algorithm algorithm = Algorithm.HMAC512(GlobalKey.JWT_SIGNING_KEY);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(GlobalKey.ISSUER).build();
        DecodedJWT jwt = verifier.verify(token);

        // Expiration
        Assert.isTrue(jwt.getExpiresAt().after(new Date()), () -> {
            throw new AuthorizationException("Token has expired.");
        });

        // Corresponding Identity should exist
        Identity identity = identityService.getByUuid(jwt.getSubject()).orElseThrow(() -> {
            throw new AuthorizationException("Identity is abnormal.");
        });

        // Identity should be at normal status
        Assert.isTrue(IdentityStatus.NORMAL.isTrue(identity.getStatus()), () -> {
            throw new AuthorizationException("Identity is abnormal.");
        });

        // Token should be at normal status
        Assert.isTrue(TokenStatus.NORMAL.isTrue(identity.getToken().getStatus()), () -> {
            throw new AuthorizationException("Token is abnormal.");
        });

        // Tokens should be matched
        Assert.isTrue(identity.getToken().getContent().equals(authVerify.getToken()), () -> {
            throw new AuthorizationException("Token is unauthenticated.");
        });

        // Verify group status
        Assert.isTrue(GroupStatus.NORMAL.isTrue(identity.getGroup().getStatus()), () -> {
            throw new AuthorizationException("Group does not exist.");
        });

        return Response.success("Verified.", identity.getUuid());
    }
}
