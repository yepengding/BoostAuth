package org.veritasopher.boostauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.veritasopher.boostauth.model.Admin;

import java.util.Optional;

/**
 * Admin Repository
 *
 * @author Yepeng Ding
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);

    Optional<Admin> findByUsernameAndStatus(String username, int status);
}
