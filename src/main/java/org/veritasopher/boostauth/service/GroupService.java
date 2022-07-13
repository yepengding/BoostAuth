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

    List<Group> getAllNormal();

    Optional<Group> getNormalById(long id);

    Optional<Group> getByName(String name);

}
