package org.veritasopher.boostauth.service;


import org.veritasopher.boostauth.model.Token;

import java.util.Optional;

public interface TokenService {
    Token create(Token token);

    Token update(Token token);

    Optional<Token> getById(long id);

    Optional<Token> getByContent(String content);
}
