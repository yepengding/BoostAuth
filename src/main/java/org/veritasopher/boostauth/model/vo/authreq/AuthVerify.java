package org.veritasopher.boostauth.model.vo.authreq;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AuthVerify {

    @NotNull(message = "Token should not be null.")
    @NotEmpty(message = "Token should not be empty.")
    private String token;
}
