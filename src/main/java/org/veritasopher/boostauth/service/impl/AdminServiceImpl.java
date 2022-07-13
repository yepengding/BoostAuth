package org.veritasopher.boostauth.service.impl;

import org.springframework.stereotype.Service;
import org.veritasopher.boostauth.model.Admin;
import org.veritasopher.boostauth.repository.AdminRepository;
import org.veritasopher.boostauth.service.AdminService;

import javax.annotation.Resource;
import java.util.Optional;


@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminRepository adminRepository;

    @Override
    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Optional<Admin> getByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
