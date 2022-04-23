package org.veritasopher.boostauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.veritasopher.boostauth.model.Identity;

public interface IdentityRepository extends JpaRepository<Identity, Long> {
    Identity findByUsername(String username);

    Identity findByUsernameAndSource(String username, String source);

    Identity findByUuid(String uuid);
}
