package io.github.zornx5.interfaces.facade.rest;

import io.github.zornx5.domain.service.UserService;
import io.github.zornx5.infrastructure.common.exception.UserNotFoundException;
import io.github.zornx5.infrastructure.repository.UserQuery;
import io.github.zornx5.interfaces.assembler.UserResponseAssembler;
import io.github.zornx5.interfaces.dto.UserChangePasswordRequest;
import io.github.zornx5.interfaces.dto.UserRegistrationRequest;
import io.github.zornx5.interfaces.dto.UserResponse;
import io.github.zornx5.interfaces.dto.UserUpdateRequest;
import io.github.zornx5.interfaces.facade.UserApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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
 * 用户 Restful 资源
 *
 * @author zornx5
 */
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@Slf4j
@Tag(name = "用户 Restful 资源")
public class UserRestResource<U, PK extends Serializable> implements UserApi<U, PK> {
    private final UserService<U, PK> userService;

    @Override
    @GetMapping("")
    public Page<UserResponse<U, PK>> page(
            @RequestParam(required = false) UserQuery query,
            @RequestParam(required = false) @PageableDefault(page = 0, size = 15) Pageable pageable) {
        return new UserResponseAssembler<U, PK>().of(userService.findAll(query, pageable));
    }

    @Override
    @GetMapping("/{id}")
    public Optional<UserResponse<U, PK>> get(@PathVariable String id) {
        return userService.findById(id).map(UserResponse::of);
    }

    @Override
    @PostMapping("")
    public UserResponse<U, PK> register(@RequestBody @Valid UserRegistrationRequest<U, PK> request) {

        return UserResponse.of(userService.save(request.assignTo(userService.create())));
    }

    @Override
    @PatchMapping("/{id}")
    public UserResponse<U, PK> update(@PathVariable String id,
                                      @RequestBody @Valid UserUpdateRequest<U, PK> request) {
        val user = userService.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return UserResponse.of(userService.update(request.assignTo(user.get())));
    }

    @Override
    @DeleteMapping("/{id}")
    public Void delete(@PathVariable String id) {
        userService.delete(id);
        return null;
    }

    @Override
    @PatchMapping("/current-password")
    public String changePassword(@RequestBody @Valid UserChangePasswordRequest request) {
        return null;
    }
}
