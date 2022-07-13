package org.veritasopher.boostauth.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthLogout {

    @NotNull(message = "UUID should not be null.")
    private String uuid;

}
