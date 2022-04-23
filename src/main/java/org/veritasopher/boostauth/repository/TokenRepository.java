package org.veritasopher.boostauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.veritasopher.boostauth.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByContent(String content);
}
