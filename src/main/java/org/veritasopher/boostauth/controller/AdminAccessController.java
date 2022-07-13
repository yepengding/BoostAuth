package org.veritasopher.boostauth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.veritasopher.boostauth.config.property.BoostAuthConfig;
import org.veritasopher.boostauth.core.dictionary.AdminStatus;
import org.veritasopher.boostauth.core.dictionary.ErrorCode;
import org.veritasopher.boostauth.core.exception.Assert;
import org.veritasopher.boostauth.core.response.Response;
import org.veritasopher.boostauth.model.Admin;
import org.veritasopher.boostauth.model.vo.req.AdminRegisterReq;
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
@RestController
@RequestMapping("/admin/access")
public class AdminAccessController {

    @Resource
    private BoostAuthConfig boostAuthConfig;

    @Resource
    private AdminService adminService;

    @PostMapping("/register")
    public Response<Admin> register(@Valid @RequestBody AdminRegisterReq adminRegisterReq) {
        // Check not super admin username
        Assert.isTrue(!boostAuthConfig.getAdminUsername().equals(adminRegisterReq.getUsername()), ErrorCode.EXIST, "Username exists.");

        // Check existence
        Assert.isTrue(adminService.getByUsername(adminRegisterReq.getUsername()).isEmpty(), ErrorCode.EXIST, "Username exists.");

        // Instantiate admin
        Admin admin = BeanUtils.copyBean(adminRegisterReq, Admin.class);
        admin.setPassword(CryptoUtils.encodeByBCrypt(adminRegisterReq.getPassword()));
        admin.setStatus(AdminStatus.NORMAL.getValue());

        return Response.success("Registered successfully", adminService.create(admin));
    }

}
