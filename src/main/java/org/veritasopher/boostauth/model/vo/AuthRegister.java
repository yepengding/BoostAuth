package org.veritasopher.boostauth.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthRegister {

    @NotNull(message = "UUID should not be null.")
    private String uuid;
}
