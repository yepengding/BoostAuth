package org.veritasopher.boostauth.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthVerify {

    @NotNull(message = "Token should not be null.")
    private String token;
}
