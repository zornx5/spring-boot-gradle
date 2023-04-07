package io.github.zornx5.domain.service;

import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.domain.entity.User;
import io.github.zornx5.domain.event.ImmutableRoleDeletedEvent;
import io.github.zornx5.domain.event.ImmutableRoleRegisteredEvent;
import io.github.zornx5.domain.event.ImmutableRoleUpdatedEvent;
import io.github.zornx5.infrastructure.common.exception.RoleNotFoundException;
import io.github.zornx5.infrastructure.repository.RoleQuery;
import io.github.zornx5.infrastructure.repository.RoleRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 角色服务实现
 *
 * @author zornx5
 */
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
@Slf4j
public class RoleServiceImpl<U extends User<U, PK>, PK extends Serializable>
        implements ApplicationEventPublisherAware, RoleService<U, PK> {

    private final RoleRepository<U, PK> roleRepository;

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(@Nonnull ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @PostConstruct
    private void init() {

    }

    @Override
    public Role<U, PK> create() {
        return roleRepository.create();
    }

    @Override
    public Role<U, PK> create(PK id) {
        return roleRepository.create(id);
    }

    @Override
    public Role<U, PK> save(Role<U, PK> entity) {
        Role<U, PK> role = roleRepository.save(entity);
        eventPublisher.publishEvent(new ImmutableRoleRegisteredEvent<>(role));
        return role;
    }

    @Override
    public void delete(Role<U, PK> entity) {
        roleRepository.delete(entity);
        eventPublisher.publishEvent(new ImmutableRoleDeletedEvent<>(entity));
    }

    @Override
    public void delete(PK id) {
        this.delete(roleRepository.findById(id).orElseThrow(RoleNotFoundException::new));
    }

    @Override
    public Role<U, PK> update(Role<U, PK> entity) {
        Role<U, PK> role = roleRepository.save(entity);
        eventPublisher.publishEvent(new ImmutableRoleUpdatedEvent<>(entity));
        return role;
    }

    @Override
    public Optional<Role<U, PK>> findById(PK id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role<U, PK>> findByQuery(RoleQuery query) {
        return roleRepository.findByQuery(query);
    }

    @Override
    public List<Role<U, PK>> findAllById(Collection<PK> ids) {
        return roleRepository.findAllById(ids);
    }

    @Override
    public Page<Role<U, PK>> findAll(RoleQuery query, Pageable pageable) {
        return roleRepository.findAll(query, pageable);
    }
}
