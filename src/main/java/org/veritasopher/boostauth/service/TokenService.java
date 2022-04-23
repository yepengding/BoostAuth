package org.veritasopher.boostauth.service;


import org.veritasopher.boostauth.model.Token;

public interface TokenService {
    Token create(Token token);

    Token update(Token token);

    Token findById(long id);

    Token findByContent(String content);
}
