package org.veritasopher.boostauth.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.veritasopher.boostauth.config.property.BoostAuthConfig;
import org.veritasopher.boostauth.core.dictionary.AdminStatus;
import org.veritasopher.boostauth.core.exception.type.BadRequestException;
import org.veritasopher.boostauth.core.exception.Assert;
import org.veritasopher.boostauth.core.response.Response;
import org.veritasopher.boostauth.model.Admin;
import org.veritasopher.boostauth.model.vo.adminreq.AdminRegisterReq;
import org.veritasopher.boostauth.service.AdminService;
import org.veritasopher.boostauth.utils.BeanUtils;
import org.veritasopher.boostauth.utils.CryptoUtils;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Admin Access Controller
 *
 * @author Yepeng Ding
 */
@Tag(name = "Admin Access Control")
@RestController("adminAccessController")
@RequestMapping("/admin/access")
public class AccessController {

    @Resource
    private BoostAuthConfig boostAuthConfig;

    @Resource
    private AdminService adminService;

    @PostMapping("/register")
    public Response<Admin> register(@Valid @RequestBody AdminRegisterReq adminRegisterReq) {
        // Check security
        Assert.isTrue(boostAuthConfig.getSecurity().equals(adminRegisterReq.getSecurity()), () -> {
            throw new BadRequestException("Unrecognized registration.");
        });

        // Check not super admin username
        Assert.isTrue(!boostAuthConfig.getAdminUsername().equals(adminRegisterReq.getUsername()), () -> {
            throw new BadRequestException("Username exists.");
        });

        // Check existence
        Assert.isTrue(adminService.getByUsername(adminRegisterReq.getUsername()).isEmpty(), () -> {
            throw new BadRequestException("Username exists.");
        });

        // Instantiate admin
        Admin admin = BeanUtils.copyBean(adminRegisterReq, Admin.class);
        admin.setPassword(CryptoUtils.encodeByBCrypt(adminRegisterReq.getPassword()));
        admin.setStatus(AdminStatus.NORMAL.getValue());

        return Response.success("Registered successfully", adminService.create(admin));
    }

}
