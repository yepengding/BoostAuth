package org.veritasopher.boostauth.model.vo.adminreq;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class GroupCreateReq {

    @NotNull(message = "Name should not be null.")
    @Size(min = 1, max = 55, message = "Name should be between 1 - 55.")
    @Pattern(regexp = "^[a-zA-Z\\d~@#$^*_.?-]+$", message = "Name contains illegal characters.")
    private String name;

    @Size(max = 128, message = "Description should be within 128.")
    private String description;

}
