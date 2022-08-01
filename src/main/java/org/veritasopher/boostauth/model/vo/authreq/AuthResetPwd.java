package org.veritasopher.boostauth.model.vo.authreq;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class AuthResetPwd {

    @NotNull(message = "Username should not be null.")
    @Size(min = 6, max = 16, message = "Username should be between 6 - 16.")
    @Pattern(regexp = "^[a-zA-Z\\d~@#$^*_.?-]+$", message = "Username contains illegal characters.")
    private String username;

    @NotNull(message = "Source should not be null.")
    @Size(min = 6, max = 16, message = "Source should be between 6 - 16.")
    @Pattern(regexp = "^[a-zA-Z\\d~@#$^*_.?-]+$", message = "Source contains illegal characters.")
    private String source;

    @NotNull(message = "Old password should not be null.")
    @Size(min = 6, max = 16, message = "Old password should be between 6 - 16.")
    @Pattern(regexp = "^[a-zA-Z\\d~@#$^*_.?-]+$", message = "Old password contains illegal characters.")
    private String oldPassword;

    @NotNull(message = "New password should not be null.")
    @Size(min = 6, max = 16, message = "New password should be between 6 - 16.")
    @Pattern(regexp = "^[a-zA-Z\\d~@#$^*_.?-]+$", message = "New password contains illegal characters.")
    private String newPassword;
}
