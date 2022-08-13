package org.veritasopher.boostauth.controller.basic;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Basic Access Authentication Controller
 *
 * @author Yepeng Ding
 */
@Tag(name = "Basic Access Authentication")
@RestController("basicAuthController")
@RequestMapping("/basic")
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok()
                .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"USER\"")
                .body("Login successfully.");
    }

}
