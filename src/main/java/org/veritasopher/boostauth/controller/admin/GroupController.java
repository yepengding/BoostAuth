package org.veritasopher.boostauth.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.veritasopher.boostauth.core.dictionary.ErrorCode;
import org.veritasopher.boostauth.core.dictionary.GroupStatus;
import org.veritasopher.boostauth.core.exception.Assert;
import org.veritasopher.boostauth.core.exception.type.BadRequestException;
import org.veritasopher.boostauth.core.response.Response;
import org.veritasopher.boostauth.model.Group;
import org.veritasopher.boostauth.model.Identity;
import org.veritasopher.boostauth.model.vo.adminreq.GroupCreateReq;
import org.veritasopher.boostauth.model.vo.adminreq.GroupUpdateReq;
import org.veritasopher.boostauth.service.GroupService;
import org.veritasopher.boostauth.service.IdentityService;
import org.veritasopher.boostauth.utils.BeanUtils;
import org.veritasopher.boostauth.utils.Validators;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Admin Group Controller
 *
 * @author Yepeng Ding
 */
@Tag(name = "Admin Group Control")
@RestController("adminGroupController")
@RequestMapping("/admin/group")
public class GroupController {

    @Resource
    private GroupService groupService;

    @Resource
    private IdentityService identityService;

    @PostMapping("/create")
    public Response<Group> create(@Valid @RequestBody GroupCreateReq groupCreateReq) {
        Assert.isTrue(groupService.getByName(groupCreateReq.getName()).isEmpty(), () ->
                new BadRequestException(ErrorCode.EXIST, "Group name has been used.")
        );

        Assert.isTrue(Validators.isJSON(groupCreateReq.getPrivilege()), () ->
                new BadRequestException(ErrorCode.INVALID, "Group privilege is invalid."));

        Group group = BeanUtils.copyBean(groupCreateReq, Group.class);
        group.setUuid(UUID.randomUUID().toString());
        group.setStatus(GroupStatus.NORMAL.getValue());

        return Response.success(groupService.create(group));
    }

    @PostMapping("/update")
    public Response<Group> update(@Valid @RequestBody GroupUpdateReq groupUpdateReq) {
        Group group = groupService.getNormalById(groupUpdateReq.getId()).orElseThrow(() ->
                new BadRequestException(ErrorCode.NOT_EXIST, "Group does not exist."));

        // Update name if changed
        if (!group.getName().equals(groupUpdateReq.getName())) {
            Assert.isTrue(groupService.getByName(groupUpdateReq.getName()).isEmpty(), () ->
                    new BadRequestException(ErrorCode.EXIST, "Group name has been used.")
            );
            group.setName(groupUpdateReq.getName());
        }

        group.setDescription(groupUpdateReq.getDescription());

        Assert.isTrue(Validators.isJSON(groupUpdateReq.getPrivilege()), () ->
                new BadRequestException(ErrorCode.INVALID, "Group privilege is invalid."));
        group.setPrivilege(groupUpdateReq.getPrivilege());

        return Response.success(groupService.update(group));
    }

    @GetMapping("/{id}")
    public Response<Group> getGroup(@PathVariable("id") long id) {
        Group group = groupService.getById(id).orElseThrow(() ->
                new BadRequestException(ErrorCode.NOT_EXIST, "Group does not exist."));

        return Response.success(group);
    }

    @GetMapping("/{id}/identities")
    public Response<List<Identity>> getGroupIdentities(@PathVariable("id") long id) {
        Assert.isTrue(groupService.getNormalById(id).isPresent(), () ->
                new BadRequestException(ErrorCode.NOT_EXIST, "Group does not exist or has abnormal status."));

        return Response.success(identityService.getAllNormalByGroup(id));
    }

    @GetMapping("/all/normal")
    public Response<List<Group>> getAllNormal() {
        return Response.success(groupService.getAllNormal());
    }

    @GetMapping("/all")
    public Response<List<Group>> getAll() {
        return Response.success(groupService.getAll());
    }

    @PostMapping("/enable/{id}")
    public Response<Group> enable(@PathVariable("id") Long id) {
        Group group = groupService.getById(id).orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXIST, "Group does not exist."));
        Assert.isTrue(GroupStatus.DISABLED.isTrue(group.getStatus()), () ->
                new BadRequestException("Group has been enabled."));

        group.setStatus(GroupStatus.NORMAL.getValue());
        return Response.success(groupService.update(group));
    }

    @PostMapping("/disable/{id}")
    public Response<Group> disable(@PathVariable("id") Long id) {
        Assert.isTrue(id != 1, () -> {
            throw new BadRequestException("Default group (id: 1) cannot be disabled.");
        });

        Group group = groupService.getNormalById(id).orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXIST, "Group does not exist or has been disabled."));
        group.setStatus(GroupStatus.DISABLED.getValue());
        return Response.success(groupService.update(group));
    }

    @PostMapping("/delete/{id}")
    public Response<Boolean> delete(@PathVariable("id") Long id) {
        Assert.isTrue(id != 1, () -> {
            throw new BadRequestException("Default group (id: 1) cannot be deleted.");
        });

        Group group = groupService.getById(id).orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXIST, "Group does not exist."));
        return Response.success(groupService.delete(group));
    }

}
