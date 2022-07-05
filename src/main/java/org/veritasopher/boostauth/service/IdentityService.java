package org.veritasopher.boostauth.service;

import org.veritasopher.boostauth.model.Identity;

import java.util.List;
import java.util.Optional;

/**
 * Identity Service Interface
 *
 * @author Yepeng Ding
 */
public interface IdentityService {
    Identity update(Identity identity);

    Optional<Identity> getById(long id);

    Identity findByUsername(String username);

    Identity findByUsernameAndSource(String username, String source);

    Identity findByUuid(String uuid);

    List<Identity> getTop100Waiting();

}
