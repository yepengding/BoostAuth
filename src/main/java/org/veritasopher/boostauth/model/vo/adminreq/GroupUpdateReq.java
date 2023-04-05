package org.veritasopher.boostauth.model.vo.adminreq;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * Group Update Request
 *
 * @author Yepeng Ding
 */
@Data
public class GroupUpdateReq {

    @NotNull(message = "Group ID should not be null.")
    @Min(value = 1, message = "Group ID is invalid")
    private Long id;

    @NotNull(message = "Name should not be null.")
    @Size(min = 1, max = 55, message = "Name should be between 1 - 55.")
    @Pattern(regexp = "^[a-zA-Z\\d~@#$^*_.?-]+$", message = "Name contains illegal characters.")
    private String name;

    @Size(max = 128, message = "Description should be within 128.")
    private String description;

    @NotEmpty(message = "Privilege should not be empty.")
    private String privilege;
}
