package org.veritasopher.boostauth.service;

import org.veritasopher.boostauth.model.Admin;

import java.util.Optional;

/**
 * Admin Service
 *
 * @author Yepeng Ding
 */
public interface AdminService {

    Admin create(Admin admin);

    Optional<Admin> getByUsername(String username);

    Optional<Admin> getNormalByUsername(String username);

}
