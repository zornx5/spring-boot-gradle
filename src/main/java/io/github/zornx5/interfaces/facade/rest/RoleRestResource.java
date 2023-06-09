package io.github.zornx5.interfaces.facade.rest;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.domain.service.ResourceService;
import io.github.zornx5.domain.service.RoleService;
import io.github.zornx5.infrastructure.common.exception.RoleNotFoundException;
import io.github.zornx5.infrastructure.repository.RoleQuery;
import io.github.zornx5.interfaces.assembler.RoleResponseAssembler;
import io.github.zornx5.interfaces.dto.RoleRegistrationRequest;
import io.github.zornx5.interfaces.dto.RoleResponse;
import io.github.zornx5.interfaces.dto.RoleUpdateRequest;
import io.github.zornx5.interfaces.facade.RoleApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
public class RoleRestResource<U extends User<U, PK>, PK extends Serializable> implements RoleApi<U, PK> {

    private final RoleService<U, PK> roleService;

    private final ResourceService<U, PK> resourceService;

    @Override
    @GetMapping("")
    public Page<RoleResponse<PK>> page(
            @Valid RoleQuery query,
            @PageableDefault(size = 15) Pageable pageable) {
        return new RoleResponseAssembler<U, PK>().of(roleService.findAll(null, pageable));
    }

    @Override
    @GetMapping("/{id}")
    public Optional<RoleResponse<PK>> get(@PathVariable Long id) {
        return roleService.findById((PK) id).map(RoleResponse::of);
    }

    @Override
    @PostMapping("")
    public RoleResponse<PK> register(@RequestBody @Valid RoleRegistrationRequest<U, PK> request) {
        return RoleResponse.of(roleService.save(request.assignTo(roleService.create(), resourceService)));
    }

    @Override
    @PatchMapping("/{id}")
    public RoleResponse<PK> update(@PathVariable Long id,
                                   @RequestBody @Valid RoleUpdateRequest<U, PK> request) {
        return RoleResponse.of(roleService.update(request.assignTo(roleService.findById((PK) id)
                .orElseThrow(() -> new RoleNotFoundException("不存在要更新的角色 " + id)), resourceService)));
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roleService.delete((PK) id);
    }
}
