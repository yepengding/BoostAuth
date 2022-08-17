package org.veritasopher.boostauth.controller.basic;

import com.sun.jdi.InternalException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.veritasopher.boostauth.core.dictionary.GroupStatus;
import org.veritasopher.boostauth.core.exception.Assert;
import org.veritasopher.boostauth.core.exception.type.AuthorizationException;
import org.veritasopher.boostauth.core.response.Response;
import org.veritasopher.boostauth.model.Identity;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Basic Access Authentication Controller
 *
 * @author Yepeng Ding
 */
@Tag(name = "Basic Access Authentication")
@RestController("basicAuthController")
@RequestMapping("/basic")
public class AuthController {

    @Resource
    private HttpServletRequest request;

    @GetMapping("/login")
    public ResponseEntity<Response<String>> login() {
        // Verify authenticated identity
        Identity identity = (Identity) request.getAttribute("identity");
        Assert.notNull(identity, () ->
                new InternalException("Unidentified behavior."));

        // Verify group status
        Assert.isTrue(GroupStatus.NORMAL.isTrue(identity.getGroup().getStatus()), () -> {
            throw new AuthorizationException("Group is abnormal");
        });

        return ResponseEntity.ok()
                .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"USER\"")
                .body(Response.success(identity.getUuid()));
    }

}
