package org.veritasopher.boostauth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.veritasopher.boostauth.config.GlobalKey;
import org.veritasopher.boostauth.core.dictionary.TokenStatus;
import org.veritasopher.boostauth.core.exception.Assert;
import org.veritasopher.boostauth.core.response.Response;
import org.veritasopher.boostauth.model.Identity;
import org.veritasopher.boostauth.model.vo.AuthVerify;
import org.veritasopher.boostauth.service.IdentityService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Verification Controller
 *
 * @author Yepeng Ding
 */
@RestController
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
    public Response<String> verifyToken(@RequestBody AuthVerify authVerify) {
        Assert.notNull(authVerify.getToken(), "Token should not be null.");
        String token = authVerify.getToken().replaceFirst("Bearer ", "");

        // Basic validation
        Assert.notNull(token, "No token information.");
        Assert.isTrue(!"".equals(token), "Token is empty.");

        // Parse JWT
        Algorithm algorithm = Algorithm.HMAC512(GlobalKey.JWT_SIGNING_KEY);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(GlobalKey.ISSUER)
                .build();
        DecodedJWT jwt = verifier.verify(token);

        // Expiration
        Assert.isTrue(jwt.getExpiresAt().after(new Date()), "Token has expired.");

        Identity identity = identityService.findByUuid(jwt.getSubject());
        // Corresponding Identity does not exist
        Assert.notNull(identity, "Token is invalid");

        Assert.isTrue(identity.getToken().getContent().equals(authVerify.getToken()) && TokenStatus.NORMAL.isTrue(identity.getToken().getStatus()), "Token is abnormal.");

        return Response.success("Verified.", identity.getUuid());
    }
}
