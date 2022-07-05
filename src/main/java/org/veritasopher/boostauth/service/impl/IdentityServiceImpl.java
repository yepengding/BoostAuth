package org.veritasopher.boostauth.service.impl;

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
    public Identity findByUsername(String username) {
        return identityRepository.findByUsername(username);
    }

    @Override
    public Identity findByUsernameAndSource(String username, String source) {
        return identityRepository.findByUsernameAndSource(username, source);
    }

    @Override
    public Identity findByUuid(String uuid) {
        return identityRepository.findByUuid(uuid);
    }

    @Override
    public List<Identity> getTop100Waiting() {
        return identityRepository.findTop100ByStatusOrderByIdDesc(IdentityStatus.PREREGISTER.getValue());
    }
}
