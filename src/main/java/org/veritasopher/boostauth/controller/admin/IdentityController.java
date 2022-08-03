package org.veritasopher.boostauth.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.veritasopher.boostauth.core.dictionary.IdentityStatus;
import org.veritasopher.boostauth.core.exception.type.BadRequestException;
import org.veritasopher.boostauth.core.exception.Assert;
import org.veritasopher.boostauth.core.response.Response;
import org.veritasopher.boostauth.model.Identity;
import org.veritasopher.boostauth.model.vo.PageVO;
import org.veritasopher.boostauth.service.IdentityService;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Admin Controller
 *
 * @author Yepeng Ding
 */
@RestController("adminIdentityController")
@RequestMapping("/admin/identity")
public class IdentityController {

    @Resource
    private IdentityService identityService;

    @GetMapping("/all/preregister")
    public Response<Page<Identity>> getAllPreregister(@Valid PageVO pageVO) {
        return Response.success(identityService.getAllPreregistration(PageRequest.of(pageVO.getIndex(), pageVO.getSize())));
    }

    @PostMapping("/approve/{id}")
    public Response<Identity> approve(@PathVariable("id") Long id) {
        Identity identity = identityService.getById(id).orElseThrow(() -> {
            throw new BadRequestException(String.format("Preregistration record (%s) does not exist.", id));
        });
        Assert.isTrue(IdentityStatus.DELETED.isFalse(identity.getStatus()), () -> {
            throw new BadRequestException(String.format("Registration record (%s) has been deleted.", id));
        });

        Assert.isTrue(IdentityStatus.NORMAL.isFalse(identity.getStatus()), () -> {
            throw new BadRequestException(String.format("Registration record (%s) has been approved.", id));
        });

        identity.setStatus(IdentityStatus.NORMAL.getValue());
        return Response.success(identityService.update(identity));
    }

    @PostMapping("/reject/{id}")
    public Response<Identity> reject(@PathVariable("id") Long id) {
        Identity identity = identityService.getById(id).orElseThrow(() -> {
            throw new BadRequestException(String.format("Preregistration record (%s) does not exist.", id));
        });
        Assert.isTrue(IdentityStatus.DELETED.isFalse(identity.getStatus()), () -> {
            throw new BadRequestException(String.format("Registration record (%s) has been deleted.", id));
        });

        Assert.isTrue(IdentityStatus.REJECTED.isFalse(identity.getStatus()), () -> {
            throw new BadRequestException(String.format("Registration record (%s) has been rejected.", id));
        });

        identity.setStatus(IdentityStatus.REJECTED.getValue());
        return Response.success(identityService.update(identity));
    }
}
