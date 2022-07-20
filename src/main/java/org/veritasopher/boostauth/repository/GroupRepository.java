package org.veritasopher.boostauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.veritasopher.boostauth.model.Group;

import java.util.List;
import java.util.Optional;

/**
 * Group Repository
 *
 * @author Yepeng Ding
 */
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findAllByStatus(int status);

    List<Group> findAllByStatusNot(int status);

    Optional<Group> findByIdAndStatus(long id, int status);

    Optional<Group> findByIdAndStatusNot(long id, int status);

    Optional<Group> findByName(String name);

    Optional<Group> findByNameAndStatus(String name, int status);
}
