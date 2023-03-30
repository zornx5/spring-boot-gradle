package io.github.zornx5.interfaces.facade.rest;

import io.github.zornx5.domain.service.RoleService;
import io.github.zornx5.interfaces.assembler.RoleAssembler;
import io.github.zornx5.interfaces.assembler.RoleResponseAssembler;
import io.github.zornx5.interfaces.dto.RoleRegistrationRequest;
import io.github.zornx5.interfaces.dto.RoleResponse;
import io.github.zornx5.interfaces.dto.RoleUpdateRequest;
import io.github.zornx5.interfaces.facade.RoleApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;


/**
 * 角色 Restful 资源
 *
 * @author zornx5
 */
@RequestMapping("/roles")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@Slf4j
@Tag(name = "角色 Restful 资源")
public class RoleRestResource<U, PK extends Serializable> implements RoleApi<U, PK> {
    private final RoleService<U, PK> roleService;

    @Override
    @GetMapping("")
    public Page<RoleResponse<U, PK>> list(Pageable pageable) {
        return new RoleResponseAssembler<U, PK>().of(roleService.findAll(null, pageable));
    }

    @Override
    @GetMapping("/{id}")
    public Optional<RoleResponse<U, PK>> get(@PathVariable String id) {
        return new RoleResponseAssembler<U, PK>().of(roleService.findById(id));
    }

    @Override
    @PostMapping("")
    public RoleResponse<U, PK> register(@RequestBody @Validated RoleRegistrationRequest<U, PK> request) {
        return RoleResponse.of(roleService.save(new RoleAssembler<U, PK>().of(
                roleService.create(UUID.randomUUID().toString()), request)));
    }

    @Override
    @PatchMapping("/{id}")
    public RoleResponse<U, PK> update(@PathVariable String id,
                                      @RequestBody @Validated RoleUpdateRequest<U, PK> request) {
        return RoleResponse.of(roleService.update(new RoleAssembler<U, PK>().of(
                roleService.findById(id), request)));
    }

    @Override
    @DeleteMapping("/{id}")
    public Void delete(@PathVariable String id) {
        roleService.delete(id);
        return null;
    }
}