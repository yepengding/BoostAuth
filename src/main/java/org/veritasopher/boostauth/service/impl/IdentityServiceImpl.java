package org.veritasopher.boostauth.service.impl;

import org.springframework.stereotype.Service;
import org.veritasopher.boostauth.model.Identity;
import org.veritasopher.boostauth.repository.IdentityRepository;
import org.veritasopher.boostauth.service.IdentityService;

import javax.annotation.Resource;

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
}
