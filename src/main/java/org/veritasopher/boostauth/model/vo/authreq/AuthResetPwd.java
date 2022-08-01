package org.veritasopher.boostauth.model.vo.authreq;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class AuthResetPwd {

    @NotNull(message = "UUID should not be null.")
    @Size(min = 6, max = 16, message = "UUID should be between 6 - 16.")
    @Pattern(regexp = "^[a-zA-Z\\d~@#$^*_.?-]+$", message = "UUID contains illegal characters.")
    private String uuid;

    @NotNull(message = "Old password should not be null.")
    @Size(min = 6, max = 16, message = "Old password should be between 6 - 16.")
    @Pattern(regexp = "^[a-zA-Z\\d~@#$^*_.?-]+$", message = "Old password contains illegal characters.")
    private String oldPassword;

    @NotNull(message = "New password should not be null.")
    @Size(min = 6, max = 16, message = "New password should be between 6 - 16.")
    @Pattern(regexp = "^[a-zA-Z\\d~@#$^*_.?-]+$", message = "New password contains illegal characters.")
    private String newPassword;
}
