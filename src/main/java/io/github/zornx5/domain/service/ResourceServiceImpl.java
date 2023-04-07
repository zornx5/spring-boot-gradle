package io.github.zornx5.domain.service;

import io.github.zornx5.domain.entity.Resource;
import io.github.zornx5.domain.entity.User;
import io.github.zornx5.domain.event.ImmutableResourceDeletedEvent;
import io.github.zornx5.domain.event.ImmutableResourceRegisteredEvent;
import io.github.zornx5.domain.event.ImmutableResourceUpdatedEvent;
import io.github.zornx5.infrastructure.common.exception.ResourceNotFoundException;
import io.github.zornx5.infrastructure.repository.ResourceQuery;
import io.github.zornx5.infrastructure.repository.ResourceRepository;
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
 * 资源服务实现
 *
 * @author zornx5
 */
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
@Slf4j
public class ResourceServiceImpl<U extends User<U, PK>, PK extends Serializable>
        implements ApplicationEventPublisherAware, ResourceService<U, PK> {

    private final ResourceRepository<U, PK> resourceRepository;

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(@Nonnull ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @PostConstruct
    private void init() {

    }

    @Override
    public Resource<U, PK> create() {
        return resourceRepository.create();
    }

    @Override
    public Resource<U, PK> create(PK id) {
        return resourceRepository.create(id);
    }

    @Override
    public Resource<U, PK> save(Resource<U, PK> entity) {
        Resource<U, PK> resource = resourceRepository.save(entity);
        eventPublisher.publishEvent(new ImmutableResourceRegisteredEvent<>(resource));
        return resource;
    }

    @Override
    public void delete(Resource<U, PK> entity) {
        resourceRepository.delete(entity);
        eventPublisher.publishEvent(new ImmutableResourceDeletedEvent<>(entity));
    }

    @Override
    public void delete(PK id) {
        this.delete(resourceRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public Resource<U, PK> update(Resource<U, PK> entity) {
        Resource<U, PK> resource = resourceRepository.save(entity);
        eventPublisher.publishEvent(new ImmutableResourceUpdatedEvent<>(entity));
        return resource;
    }

    @Override
    public Optional<Resource<U, PK>> findById(PK id) {
        return resourceRepository.findById(id);
    }

    @Override
    public Optional<Resource<U, PK>> findByQuery(ResourceQuery query) {
        return resourceRepository.findByQuery(query);
    }

    @Override
    public List<Resource<U, PK>> findAllById(Collection<PK> ids) {
        return resourceRepository.findAllById(ids);
    }

    @Override
    public Page<Resource<U, PK>> findAll(ResourceQuery query, Pageable pageable) {
        return resourceRepository.findAll(query, pageable);
    }
}
