package org.veritasopher.boostauth.service.impl;

import org.springframework.stereotype.Service;
import org.veritasopher.boostauth.model.Token;
import org.veritasopher.boostauth.repository.TokenRepository;
import org.veritasopher.boostauth.service.TokenService;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Token Service
 *
 * @author Yepeng Ding
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Resource
    private TokenRepository tokenRepository;

    @Override
    public Token create(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public Token update(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public Token findById(long id) {
        Token token = null;
        Optional<Token> result = tokenRepository.findById(id);
        if (result.isPresent()) {
            token = result.get();
        }
        return token;
    }

    @Override
    public Token findByContent(String content) {
        return tokenRepository.findByContent(content);
    }
}
