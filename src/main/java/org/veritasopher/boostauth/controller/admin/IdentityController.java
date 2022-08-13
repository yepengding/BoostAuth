package org.veritasopher.boostauth.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.veritasopher.boostauth.core.dictionary.ErrorCode;
import org.veritasopher.boostauth.core.dictionary.IdentityStatus;
import org.veritasopher.boostauth.core.dictionary.TokenStatus;
import org.veritasopher.boostauth.core.exception.type.BadRequestException;
import org.veritasopher.boostauth.core.exception.Assert;
import org.veritasopher.boostauth.core.response.Response;
import org.veritasopher.boostauth.model.Identity;
import org.veritasopher.boostauth.model.Token;
import org.veritasopher.boostauth.model.vo.PageVO;
import org.veritasopher.boostauth.model.vo.adminreq.IdentityRegisterReq;
import org.veritasopher.boostauth.service.GroupService;
import org.veritasopher.boostauth.service.IdentityService;
import org.veritasopher.boostauth.service.TokenService;
import org.veritasopher.boostauth.utils.CryptoUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Admin Identity Controller
 *
 * @author Yepeng Ding
 */
@Tag(name = "Admin Identity Control")
@RestController("adminIdentityController")
@RequestMapping("/admin/identity")
public class IdentityController {

    @Resource
    private IdentityService identityService;

    @Resource
    private TokenService tokenService;

    @Resource
    private GroupService groupService;

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

    @PostMapping("/register")
    public Response<String> register(@Valid @RequestBody IdentityRegisterReq identityRegisterReq) {
        // Check existence.
        Assert.isTrue(identityService.getByUsernameAndSource(identityRegisterReq.getUsername(), identityRegisterReq.getSource()).isEmpty(), () -> {
            throw new BadRequestException(ErrorCode.EXIST, "Username exists.");
        });

        // Check group existence
        Assert.isTrue(groupService.getNormalById(identityRegisterReq.getGroupId()).isPresent(), () -> {
            throw new BadRequestException("Group does not exist.");
        });

        Identity identity = new Identity();
        identity.setUuid(UUID.randomUUID().toString());
        identity.setUsername(identityRegisterReq.getUsername());
        identity.setPassword(CryptoUtils.encodeByBCrypt(identityRegisterReq.getPassword()));
        identity.setSource(identityRegisterReq.getSource());
        identity.setGroupId(identityRegisterReq.getGroupId());
        identity.setStatus(IdentityStatus.NORMAL.getValue());

        // Create one-to-one token
        Token token = new Token();
        token.setStatus(TokenStatus.INVALID.getValue());
        tokenService.create(token);
        identity.setToken(token);

        identityService.update(identity);
        return Response.success("Preregister successfully.", identity.getUuid());
    }


    @GetMapping("/all/preregister")
    public Response<Page<Identity>> getAllPreregister(@Valid PageVO pageVO) {
        return Response.success(identityService.getAllPreregistration(PageRequest.of(pageVO.getIndex(), pageVO.getSize())));
    }
}
