package org.veritasopher.boostauth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.veritasopher.boostauth.model.Identity;

import java.util.List;
import java.util.Optional;

/**
 * Identity Repository
 *
 * @author Yepeng Ding
 */
public interface IdentityRepository extends PagingAndSortingRepository<Identity, Long> {
    Optional<Identity> findByUsername(String username);

    Optional<Identity> findByUsernameAndSource(String username, String source);

    Optional<Identity> findByUuid(String uuid);

    List<Identity> findAllByGroupIdAndStatus(long groupId, int status);

    Page<Identity> findAllByStatusOrderByIdDesc(int status, Pageable pageable);
}
