package org.veritasopher.boostauth.model.vo;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class PageVO {

    @NotNull(message = "Page index should not be null")
    @PositiveOrZero(message = "Page index should be greater than or equal to 0.")
    private Integer index;

    @NotNull(message = "Page size should not be null")
    @Positive(message = "Page size should be greater than 0.")
    private Integer size;

}
