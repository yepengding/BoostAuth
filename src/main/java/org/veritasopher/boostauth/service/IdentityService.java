package org.veritasopher.boostauth.service;

import org.veritasopher.boostauth.model.Identity;

public interface IdentityService {
    Identity update(Identity identity);

    Identity findByUsername(String username);

    Identity findByUsernameAndSource(String username, String source);

    Identity findByUuid(String uuid);
}
