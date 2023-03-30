package io.github.zornx5.domain.service;

import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.domain.event.ImmutableRoleDeletedEvent;
import io.github.zornx5.domain.event.ImmutableRoleRegisteredEvent;
import io.github.zornx5.domain.event.ImmutableRoleUpdatedEvent;
import io.github.zornx5.infrastructure.common.exception.UserNotFoundException;
import io.github.zornx5.infrastructure.repository.RoleQuery;
import io.github.zornx5.infrastructure.repository.RoleRepository;
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
public class RoleServiceImpl<U, PK extends Serializable>
        implements ApplicationEventPublisherAware, RoleService<U, PK> {

    private final RoleRepository<U, PK> repository;

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @PostConstruct
    private void init() {

    }

    @Override
    public Role<U, PK> create() {
        return null;
    }

    @Override
    public Role<U, PK> create(PK id) {
        return repository.create(id);
    }

    @Override
    public Role<U, PK> save(Role<U, PK> entity) {
        Role<U, PK> role = repository.save(entity);
        eventPublisher.publishEvent(new ImmutableRoleRegisteredEvent<>(role));
        return role;
    }

    @Override
    public void delete(Role<U, PK> entity) {
        repository.delete(entity);
        eventPublisher.publishEvent(new ImmutableRoleDeletedEvent<>(entity));
    }

    @Override
    public void delete(PK id) {
        Optional<Role<U, PK>> role = repository.findById(id);
        if (role.isEmpty()) {
            throw new UserNotFoundException();
        }
        this.delete(role.get());
    }

    @Override
    public Role<U, PK> update(Role<U, PK> entity) {
        Role<U, PK> role = repository.save(entity);
        eventPublisher.publishEvent(new ImmutableRoleUpdatedEvent<>(entity));
        return role;
    }

    @Override
    public Optional<Role<U, PK>> findById(PK id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Role<U, PK>> findByQuery(RoleQuery query) {
        return repository.findByQuery(query);
    }

    @Override
    public List<Role<U, PK>> findAllById(Collection<PK> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Page<Role<U, PK>> findAll(RoleQuery query, Pageable pageable) {
        return repository.findAll(query, pageable);
    }
}
