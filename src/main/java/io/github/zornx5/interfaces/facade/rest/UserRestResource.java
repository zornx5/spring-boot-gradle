package io.github.zornx5.interfaces.facade.rest;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.domain.service.ResourceService;
import io.github.zornx5.domain.service.RoleService;
import io.github.zornx5.domain.service.UserService;
import io.github.zornx5.infrastructure.common.enums.ResourceType;
import io.github.zornx5.infrastructure.common.enums.UserGender;
import io.github.zornx5.infrastructure.common.enums.UserStatus;
import io.github.zornx5.infrastructure.common.exception.UserNotFoundException;
import io.github.zornx5.infrastructure.repository.UserQuery;
import io.github.zornx5.interfaces.assembler.UserResponseAssembler;
import io.github.zornx5.interfaces.dto.UserRegistrationRequest;
import io.github.zornx5.interfaces.dto.UserResponse;
import io.github.zornx5.interfaces.dto.UserUpdateRequest;
import io.github.zornx5.interfaces.facade.UserApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
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
public class UserRestResource<U extends User<U, PK>, PK extends Serializable> implements UserApi<U, PK> {

    private final UserService<U, PK> userService;

    private final RoleService<U, PK> roleService;

    private final ResourceService<U, PK> resourceService;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // 资源创建
        var rootMenu = resourceService.save(resourceService.create().toBuilder()
                .name("root")
                .description("根结点")
                .type(ResourceType.MENU)
                .permission("*")
                .icon(null)
                .url(null)
                .parent(null)
                .build());
        var dashboardMenu = resourceService.save(resourceService.create().toBuilder()
                .name("dashboard")
                .description("仪表盘")
                .type(ResourceType.MENU)
                .permission("dashboard")
                .icon("Odometer")
                .url("/dashboard")
                .parent(rootMenu)
                .build());
        var systemMenu = resourceService.save(resourceService.create().toBuilder()
                .name("system")
                .description("系统管理")
                .type(ResourceType.MENU)
                .permission("system")
                .icon("SetUp")
                .url("/system")
                .parent(rootMenu)
                .build());
        var usersMenu = resourceService.save(resourceService.create().toBuilder()
                .name("users")
                .description("用户管理")
                .type(ResourceType.MENU)
                .permission("users:view")
                .icon("User")
                .url("/users")
                .parent(systemMenu)
                .build());
        var usersCreateButton = resourceService.save(resourceService.create().toBuilder()
                .name("users-create")
                .description("用户创建按钮")
                .type(ResourceType.BUTTON)
                .permission("users:create")
                .icon("icon-create")
                .url(null)
                .parent(usersMenu)
                .build());
        var usersUpdateButton = resourceService.save(resourceService.create().toBuilder()
                .name("users-update")
                .description("用户更新按钮")
                .type(ResourceType.BUTTON)
                .permission("users:update")
                .icon("icon-update")
                .url(null)
                .parent(usersMenu)
                .build());
        var usersDeleteButton = resourceService.save(resourceService.create().toBuilder()
                .name("users-delete")
                .description("用户删除按钮")
                .type(ResourceType.BUTTON)
                .permission("users:delete")
                .icon("icon-delete")
                .url(null)
                .parent(usersMenu)
                .build());
        var rolesMenu = resourceService.save(resourceService.create().toBuilder()
                .name("roles")
                .description("角色管理列表")
                .type(ResourceType.MENU)
                .permission("roles:view")
                .icon("Tickets")
                .url("/roles")
                .parent(systemMenu)
                .build());
        var rolesCreateButton = resourceService.save(resourceService.create().toBuilder()
                .name("roles-create")
                .description("角色创建按钮")
                .type(ResourceType.BUTTON)
                .permission("roles:create")
                .icon("icon-create")
                .url(null)
                .parent(rolesMenu)
                .build());
        var rolesUpdateButton = resourceService.save(resourceService.create().toBuilder()
                .name("roles-update")
                .description("角色更新按钮")
                .type(ResourceType.BUTTON)
                .permission("roles:update")
                .icon("icon-update")
                .url(null)
                .parent(rolesMenu)
                .build());
        var rolesDeleteButton = resourceService.save(resourceService.create().toBuilder()
                .name("roles-delete")
                .description("角色删除按钮")
                .type(ResourceType.BUTTON)
                .permission("roles:delete")
                .icon("icon-delete")
                .url(null)
                .parent(rolesMenu)
                .build());
        var resourcesMenu = resourceService.save(resourceService.create().toBuilder()
                .name("resources")
                .description("资源管理列表")
                .type(ResourceType.MENU)
                .permission("resources:view")
                .icon("Collection")
                .url("/resources")
                .parent(systemMenu)
                .build());
        var resourcesCreateButton = resourceService.save(resourceService.create().toBuilder()
                .name("resources-create")
                .description("资源创建按钮")
                .type(ResourceType.BUTTON)
                .permission("resources:create")
                .icon("icon-create")
                .url(null)
                .parent(resourcesMenu)
                .build());
        var resourcesUpdateButton = resourceService.save(resourceService.create().toBuilder()
                .name("resources-update")
                .description("资源更新按钮")
                .type(ResourceType.BUTTON)
                .permission("resources:update")
                .icon("icon-update")
                .url(null)
                .parent(resourcesMenu)
                .build());
        var resourcesDeleteButton = resourceService.save(resourceService.create().toBuilder()
                .name("resources-delete")
                .description("资源删除按钮")
                .type(ResourceType.BUTTON)
                .permission("resources:delete")
                .icon("icon-delete")
                .url(null)
                .parent(resourcesMenu)
                .build());

        var adminRole = roleService.save(roleService.create().toBuilder()
                .name("Admin")
                .description("Administrator")
                .resources(List.of(rootMenu, dashboardMenu, systemMenu,
                        usersMenu, usersCreateButton, usersUpdateButton, usersDeleteButton,
                        rolesMenu, rolesCreateButton, rolesUpdateButton, rolesDeleteButton,
                        resourcesMenu, resourcesCreateButton, resourcesUpdateButton, resourcesDeleteButton))
                .build());
        var userRole = roleService.save(roleService.create().toBuilder()
                .name("User")
                .description("User")
                .resources(List.of(rootMenu, dashboardMenu, systemMenu,
                        usersMenu,
                        rolesMenu,
                        resourcesMenu))
                .build());
        var reportRole = roleService.save(roleService.create().toBuilder()
                .name("Report")
                .description("Report user")
                .resources(List.of(rootMenu, dashboardMenu))
                .build());

        var adminUser = userService.save(userService.create().toBuilder()
                .name("admin")
                .firstName("admin")
                .lastName("system")
                .description("超级管理员")
                .age(18)
                .address("localhost:00")
                .email("admin@zornx5.github.io")
                .phone("+86 13988000000")
                .password(passwordEncoder.encode("admin"))
                .status(UserStatus.ACTIVE)
                .gender(UserGender.MALE)
                .expiredTime(LocalDateTime.now().plusYears(3))
                .roles(List.of(adminRole))
                .build());
        var admin1User = userService.save(userService.create().toBuilder()
                .name("admin1")
                .firstName("admin1")
                .lastName("system")
                .description("超级管理员1")
                .age(18)
                .address("localhost:01")
                .email("admin1@zornx5.github.io")
                .phone("+86 13988000001")
                .password(passwordEncoder.encode("admin1"))
                .status(UserStatus.DRAFT)
                .gender(UserGender.MALE)
                .expiredTime(LocalDateTime.now().plusYears(3))
                .roles(List.of(adminRole))
                .build());
        var admin2User = userService.save(userService.create().toBuilder()
                .name("admin2")
                .firstName("admin2")
                .lastName("system")
                .description("超级管理员2")
                .age(18)
                .address("localhost:02")
                .email("admin2@zornx5.github.io")
                .phone("+86 13988000002")
                .password(passwordEncoder.encode("admin2"))
                .status(UserStatus.LOCKED)
                .gender(UserGender.MALE)
                .expiredTime(LocalDateTime.now().plusYears(3))
                .roles(List.of(adminRole))
                .build());
        var admin3User = userService.save(userService.create().toBuilder()
                .name("admin3")
                .firstName("admin3")
                .lastName("system")
                .description("超级管理员3")
                .age(18)
                .address("localhost:03")
                .email("admin3@zornx5.github.io")
                .phone("+86 13988000003")
                .password(passwordEncoder.encode("admin3"))
                .status(UserStatus.LOCKED)
                .gender(UserGender.MALE)
                .expiredTime(LocalDateTime.now().minusSeconds(1))
                .roles(List.of(adminRole))
                .build());
        var admin4User = userService.save(userService.create().toBuilder()
                .name("admin4")
                .firstName("admin4")
                .lastName("system")
                .description("超级管理员4")
                .age(18)
                .address("localhost:04")
                .email("admin4@zornx5.github.io")
                .phone("+86 13988000004")
                .password(passwordEncoder.encode("admin4"))
                .status(UserStatus.ACTIVE)
                .gender(UserGender.FEMALE)
                .expiredTime(LocalDateTime.now().plusDays(1))
                .roles(List.of(adminRole, userRole, reportRole))
                .build());

        var userUser = userService.save(userService.create().toBuilder()
                .name("user")
                .firstName("user")
                .lastName("system")
                .description("普通用户")
                .age(18)
                .address("localhost:10")
                .email("user@zornx5.github.io")
                .phone("+86 13988000010")
                .password(passwordEncoder.encode("user"))
                .status(UserStatus.ACTIVE)
                .gender(UserGender.MALE)
                .expiredTime(LocalDateTime.now().plusYears(3))
                .roles(List.of(userRole))
                .build());

        var reportUser = userService.save(userService.create().toBuilder()
                .name("report")
                .firstName("report")
                .lastName("system")
                .description("报告员")
                .age(18)
                .address("localhost:20")
                .email("report@zornx5.github.io")
                .phone("+86 13988000020")
                .password(passwordEncoder.encode("report"))
                .status(UserStatus.ACTIVE)
                .gender(UserGender.MALE)
                .expiredTime(LocalDateTime.now().plusYears(3))
                .roles(List.of(reportRole))
                .build());
    }

    @Override
    @GetMapping("")
    public Page<UserResponse<PK>> page(
            @Valid UserQuery query,
            @PageableDefault(size = 15) Pageable pageable) {
        return new UserResponseAssembler<U, PK>().of(userService.findAll(query, pageable));
    }

    @Override
    @GetMapping("/{id}")
    public Optional<UserResponse<PK>> get(@PathVariable Long id) {
        return userService.findById((PK) id).map(UserResponse::of);
    }

    @Override
    @PostMapping("")
    public UserResponse<PK> register(@RequestBody @Valid UserRegistrationRequest<U, PK> request) {
        return UserResponse.of(userService.save(request.assignTo(userService.create(), passwordEncoder, roleService)));
    }

    @Override
    @PatchMapping("/{id}")
    public UserResponse<PK> update(@PathVariable Long id,
                                   @RequestBody @Valid UserUpdateRequest<U, PK> request) {
        return UserResponse.of(userService.update(request.assignTo(userService.findById((PK) id)
                .orElseThrow(() -> new UserNotFoundException("不存在要更新按钮的用户 " + id)), roleService)));
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete((PK) id);
    }
}
