package org.veritasopher.boostauth.model.vo;

import lombok.Data;

@Data
public class AuthPreregister {

    private String username;

    private String password;

    private String source;
}
