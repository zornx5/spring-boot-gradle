package io.github.zornx5.domain.service;

import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.domain.event.ImmutableResourceDeletedEvent;
import io.github.zornx5.domain.event.ImmutableResourceRegisteredEvent;
import io.github.zornx5.domain.event.ImmutableResourceUpdatedEvent;
import io.github.zornx5.infrastructure.common.exception.UserNotFoundException;
import io.github.zornx5.infrastructure.repository.ResourceQuery;
import io.github.zornx5.infrastructure.repository.ResourceRepository;
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
 * 资源服务实现
 *
 * @author zornx5
 */
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
@Slf4j
public class ResourceServiceImpl<U, PK extends Serializable>
        implements ApplicationEventPublisherAware, ResourceService<U, PK> {

    private final ResourceRepository<U, PK> repository;

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @PostConstruct
    private void init() {

    }

    @Override
    public Resource<U, PK> create() {
        return null;
    }

    @Override
    public Resource<U, PK> create(String id) {
        return repository.create(id);
    }

    @Override
    public Resource<U, PK> save(Resource<U, PK> entity) {
        Resource<U, PK> resource = repository.save(entity);
        eventPublisher.publishEvent(new ImmutableResourceRegisteredEvent<>(resource));
        return resource;
    }

    @Override
    public void delete(Resource<U, PK> entity) {
        repository.delete(entity);
        eventPublisher.publishEvent(new ImmutableResourceDeletedEvent<>(entity));
    }

    @Override
    public void delete(String id) {
        Optional<Resource<U, PK>> resource = repository.findById(id);
        if (resource.isEmpty()) {
            throw new UserNotFoundException();
        }
        this.delete(resource.get());
    }

    @Override
    public Resource<U, PK> update(Resource<U, PK> entity) {
        Resource<U, PK> resource = repository.save(entity);
        eventPublisher.publishEvent(new ImmutableResourceUpdatedEvent<>(entity));
        return resource;
    }

    @Override
    public Optional<Resource<U, PK>> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Resource<U, PK>> findByQuery(ResourceQuery query) {
        return repository.findByQuery(query);
    }

    @Override
    public List<Resource<U, PK>> findAllById(Collection<String> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Page<Resource<U, PK>> findAll(ResourceQuery query, Pageable pageable) {
        return repository.findAll(query, pageable);
    }
}
