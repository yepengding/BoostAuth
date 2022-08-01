package org.veritasopher.boostauth.model.vo.adminreq;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
public class AdminRegisterReq {

    @NotNull(message = "Username should not be null.")
    @Size(min = 6, max = 16, message = "Username should be between 6 - 16.")
    @Pattern(regexp = "^[a-zA-Z\\d~@#$^*_.?-]+$", message = "Username contains illegal characters.")
    private String username;

    @NotNull(message = "Password should not be null.")
    @Size(min = 6, max = 16, message = "Password should be between 6 - 16.")
    @Pattern(regexp = "^[a-zA-Z\\d~@#$^*_.?-]+$", message = "Password contains illegal characters.")
    private String password;

    @PositiveOrZero(message = "Level should be positive or zero.")
    private Integer level;

}
