package org.veritasopher.boostauth.controller.basic;

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
@RestController
@RequestMapping("/basic")
public class BasicAuthController {


    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok()
                .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"USER\"")
                .body("Login successfully.");
    }

}
