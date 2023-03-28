package io.github.zornx5.interfaces.facade.rest;

import io.github.zornx5.domain.service.ResourceService;
import io.github.zornx5.interfaces.assembler.ResourceAssembler;
import io.github.zornx5.interfaces.assembler.ResourceResponseAssembler;
import io.github.zornx5.interfaces.dto.ResourceRegistrationRequest;
import io.github.zornx5.interfaces.dto.ResourceResponse;
import io.github.zornx5.interfaces.dto.ResourceUpdateRequest;
import io.github.zornx5.interfaces.facade.ResourceApi;
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
 * 资源 Restful 资源
 *
 * @author zornx5
 */
@RequestMapping("/resources")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@Slf4j
@Tag(name = "资源 Restful 资源")
public class ResourceRestResource<U, PK extends Serializable> implements ResourceApi<U, PK> {
    private final ResourceService<U, PK> resourceService;

    @Override
    @GetMapping("")
    public Page<ResourceResponse<U, PK>> list(Pageable pageable) {
        return new ResourceResponseAssembler<U, PK>().of(resourceService.findAll(null, pageable));
    }

    @Override
    @GetMapping("/{id}")
    public Optional<ResourceResponse<U, PK>> get(@PathVariable String id) {
        return new ResourceResponseAssembler<U, PK>().of(resourceService.findById(id));
    }

    @Override
    @PostMapping("")
    public ResourceResponse<U, PK> register(@RequestBody @Validated ResourceRegistrationRequest<U, PK> request) {
        return ResourceResponse.of(resourceService.save(new ResourceAssembler<U, PK>().of(
                resourceService.create(UUID.randomUUID().toString()), request)));
    }

    @Override
    @PatchMapping("/{id}")
    public ResourceResponse<U, PK> update(@PathVariable String id,
                                          @RequestBody @Validated ResourceUpdateRequest<U, PK> request) {
        return ResourceResponse.of(resourceService.update(new ResourceAssembler<U, PK>().of(
                resourceService.findById(id), request)));
    }

    @Override
    @DeleteMapping("/{id}")
    public Void delete(@PathVariable String id) {
        resourceService.delete(id);
        return null;
    }
}
