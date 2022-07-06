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
    public Optional<Token> getById(long id) {
        return tokenRepository.findById(id);
    }

    @Override
    public Optional<Token> getByContent(String content) {
        return tokenRepository.findByContent(content);
    }
}
