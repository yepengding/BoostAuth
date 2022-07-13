package org.veritasopher.boostauth.controller.admin;

import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;
import org.veritasopher.boostauth.core.dictionary.ErrorCode;
import org.veritasopher.boostauth.core.dictionary.GroupStatus;
import org.veritasopher.boostauth.core.exception.Assert;
import org.veritasopher.boostauth.core.exception.SystemException;
import org.veritasopher.boostauth.core.response.Response;
import org.veritasopher.boostauth.model.Group;
import org.veritasopher.boostauth.model.Identity;
import org.veritasopher.boostauth.model.vo.req.GroupCreateReq;
import org.veritasopher.boostauth.service.GroupService;
import org.veritasopher.boostauth.service.IdentityService;
import org.veritasopher.boostauth.utils.BeanUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Admin Group Controller
 *
 * @author Yepeng Ding
 */
@RestController("adminGroupController")
@RequestMapping("/admin/group")
public class GroupController {

    @Resource
    private GroupService groupService;

    @Resource
    private IdentityService identityService;

    @PostMapping("/create")
    public Response<Group> create(@Valid @RequestBody GroupCreateReq groupCreateReq) {
        Assert.isTrue(groupService.getByName(groupCreateReq.getName()).isEmpty(), ErrorCode.EXIST.getValue(),
                "Group name has been used.");

        Group group = BeanUtils.copyBean(groupCreateReq, Group.class);
        group.setStatus(GroupStatus.NORMAL.getValue());

        return Response.success(groupService.create(group));
    }

    @GetMapping("/{id}")
    public Response<List<Identity>> getGroupIdentities(@PathVariable("id") long id) {
        Assert.isTrue(groupService.getNormalById(id).isPresent(),
                "Group does not exist.");

        return Response.success(identityService.getAllNormalByGroup(id));
    }

    @GetMapping("/all/normal")
    public Response<List<Group>> getAllNormal() {
        return Response.success(groupService.getAllNormal());
    }

}
