package org.veritasopher.boostauth.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Optional<Identity> getByUsername(String username);

    Optional<Identity> getByUsernameAndSource(String username, String source);

    Optional<Identity> getByUuid(String uuid);

    List<Identity> getAllNormalByGroup(long id);

    Page<Identity> getAllPreregistration(Pageable pageable);

}
