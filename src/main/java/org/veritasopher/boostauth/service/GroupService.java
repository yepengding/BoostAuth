package org.veritasopher.boostauth.service;

import org.veritasopher.boostauth.model.Group;

import java.util.List;
import java.util.Optional;

/**
 * Group Service
 *
 * @author Yepeng Ding
 */
public interface GroupService {

    Group create(Group group);

    List<Group> getAll();

    List<Group> getAllNormal();

    Optional<Group> getById(long id);

    Optional<Group> getNormalById(long id);

    Optional<Group> getByName(String name);

    Group update(Group group);

    boolean delete(Group group);

}
