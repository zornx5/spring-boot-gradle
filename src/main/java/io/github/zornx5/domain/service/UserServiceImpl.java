package io.github.zornx5.domain.service;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.domain.event.ImmutableUserDeletedEvent;
import io.github.zornx5.domain.event.ImmutableUserRegisteredEvent;
import io.github.zornx5.domain.event.ImmutableUserUpdatedEvent;
import io.github.zornx5.infrastructure.common.exception.UserNotFoundException;
import io.github.zornx5.infrastructure.repository.UserQuery;
import io.github.zornx5.infrastructure.repository.UserRepository;
import jakarta.annotation.Nonnull;
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
 * 用户服务实现
 *
 * @author zornx5
 */
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
@Slf4j
public class UserServiceImpl<U extends User<U, PK>, PK extends Serializable>
        implements ApplicationEventPublisherAware, UserService<U, PK> {
    private final UserRepository<U, PK> userRepository;

//    private final PasswordEncoder passwordEncoder;

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(@Nonnull ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public User<U, PK> create() {
        return userRepository.create();
    }

    @Override
    public User<U, PK> create(PK id) {
        return userRepository.create(id);
    }

    @Override
    public User<U, PK> save(User<U, PK> entity) {
//        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        User<U, PK> user = userRepository.save(entity);
        eventPublisher.publishEvent(new ImmutableUserRegisteredEvent<>(user));
        return user;
    }

    @Override
    public void delete(User<U, PK> entity) {
        userRepository.delete(entity);
        eventPublisher.publishEvent(new ImmutableUserDeletedEvent<>(entity));
    }

    @Override
    public void delete(PK id) {
        this.delete(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public User<U, PK> update(User<U, PK> entity) {
        User<U, PK> user = userRepository.save(entity);
        eventPublisher.publishEvent(new ImmutableUserUpdatedEvent<>(entity));
        return user;
    }

    @Override
    public Optional<User<U, PK>> findById(PK id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User<U, PK>> findByQuery(UserQuery query) {
        return userRepository.findByQuery(query);
    }

    @Override
    public List<User<U, PK>> findAllById(Collection<PK> ids) {
        return userRepository.findAllById(ids);
    }

    @Override
    public Page<User<U, PK>> findAll(UserQuery query, Pageable pageable) {
        return userRepository.findAll(query, pageable);
    }
}
