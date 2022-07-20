package org.veritasopher.boostauth.service.impl;

import org.springframework.stereotype.Service;
import org.veritasopher.boostauth.core.dictionary.GroupStatus;
import org.veritasopher.boostauth.model.Group;
import org.veritasopher.boostauth.repository.GroupRepository;
import org.veritasopher.boostauth.service.GroupService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service("groupService")
public class GroupServiceImpl implements GroupService {

    @Resource
    private GroupRepository groupRepository;

    @Override
    public Group create(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public List<Group> getAll() {
        return groupRepository.findAllByStatusNot(GroupStatus.DELETED.getValue());
    }

    @Override
    public List<Group> getAllNormal() {
        return groupRepository.findAllByStatus(GroupStatus.NORMAL.getValue());
    }

    @Override
    public Optional<Group> getById(long id) {
        return groupRepository.findByIdAndStatusNot(id, GroupStatus.DELETED.getValue());
    }

    @Override
    public Optional<Group> getNormalById(long id) {
        return groupRepository.findByIdAndStatus(id, GroupStatus.NORMAL.getValue());
    }

    @Override
    public Optional<Group> getByName(String name) {
        return groupRepository.findByName(name);
    }

    @Override
    public Group update(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public boolean delete(Group group) {
        group.setStatus(GroupStatus.DELETED.getValue());
        groupRepository.save(group);
        return true;
    }
}
