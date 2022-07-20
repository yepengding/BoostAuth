package org.veritasopher.boostauth.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.veritasopher.boostauth.core.dictionary.IdentityStatus;
import org.veritasopher.boostauth.model.Identity;
import org.veritasopher.boostauth.repository.IdentityRepository;
import org.veritasopher.boostauth.service.IdentityService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * Identity Service
 *
 * @author Yepeng Ding
 */
@Service("identityService")
public class IdentityServiceImpl implements IdentityService {

    @Resource
    private IdentityRepository identityRepository;

    @Override
    public Identity update(Identity identity) {
        return identityRepository.save(identity);
    }

    @Override
    public Optional<Identity> getById(long id) {
        return identityRepository.findById(id);
    }

    @Override
    public Optional<Identity> getByUsername(String username) {
        return identityRepository.findByUsername(username);
    }

    @Override
    public Optional<Identity> getByUsernameAndSource(String username, String source) {
        return identityRepository.findByUsernameAndSource(username, source);
    }

    @Override
    public Optional<Identity> getByUuid(String uuid) {
        return identityRepository.findByUuid(uuid);
    }

    @Override
    public List<Identity> getAllNormalByGroup(long id) {
        return identityRepository.findAllByGroupIdAndStatus(id, IdentityStatus.NORMAL.getValue());
    }

    @Override
    public Page<Identity> getAllPreregistration(Pageable pageable) {
        return identityRepository.findAllByStatusOrderByIdDesc(IdentityStatus.PREREGISTER.getValue(), pageable);
    }
}
