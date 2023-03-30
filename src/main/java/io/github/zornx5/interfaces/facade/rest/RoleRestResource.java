package io.github.zornx5.interfaces.facade.rest;

import io.github.zornx5.domain.service.RoleService;
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
import org.springframework.web.bind.annotation.RequestParam;
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
public class RoleRestResource<U, PK extends Serializable> implements RoleApi<U, PK> {
    private final RoleService<U, PK> roleService;

    @Override
    @GetMapping("")
    public Page<RoleResponse<U, PK>> page(
            @RequestParam(required = false) RoleQuery query,
            @RequestParam(required = false) @PageableDefault(page = 0, size = 15) Pageable pageable) {
        return new RoleResponseAssembler<U, PK>().of(roleService.findAll(null, pageable));
    }

    @Override
    @GetMapping("/{id}")
    public Optional<RoleResponse<U, PK>> get(@PathVariable String id) {
        return RoleResponse.of(roleService.findById(id));
    }

    @Override
    @PostMapping("")
    public RoleResponse<U, PK> register(@RequestBody @Valid RoleRegistrationRequest<U, PK> request) {
        return RoleResponse.of(roleService.save(request.assignTo(roleService.create())));
    }

    @Override
    @PatchMapping("/{id}")
    public RoleResponse<U, PK> update(@PathVariable String id,
                                      @RequestBody @Valid RoleUpdateRequest<U, PK> request) {
        return RoleResponse.of(roleService.update(request.assignTo(roleService.findById(id))));
    }

    @Override
    @DeleteMapping("/{id}")
    public Void delete(@PathVariable String id) {
        roleService.delete(id);
        return null;
    }
}
