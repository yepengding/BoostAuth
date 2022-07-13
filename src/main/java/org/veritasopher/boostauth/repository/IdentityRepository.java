package org.veritasopher.boostauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.veritasopher.boostauth.model.Identity;

import java.util.List;
import java.util.Optional;

/**
 * Identity Repository
 *
 * @author Yepeng Ding
 */
public interface IdentityRepository extends JpaRepository<Identity, Long> {
    Optional<Identity> findByUsername(String username);

    Optional<Identity> findByUsernameAndSource(String username, String source);

    Optional<Identity> findByUuid(String uuid);

    List<Identity> findAllByGroupIdAndStatus(long groupId, int status);

    List<Identity> findTop100ByStatusOrderByIdDesc(int status);
}
