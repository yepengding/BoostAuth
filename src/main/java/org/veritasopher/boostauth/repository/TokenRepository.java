package org.veritasopher.boostauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.veritasopher.boostauth.model.Token;

import java.util.Optional;

/**
 * Token Repository
 *
 * @author Yepeng Ding
 */
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByContent(String content);
}
