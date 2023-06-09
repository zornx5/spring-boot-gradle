package io.github.zornx5.infrastructure.repository.jpa;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.domain.entity.jpa.JpaUser;
import io.github.zornx5.infrastructure.repository.UserQuery;
import io.github.zornx5.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Jpa 用户存储实现
 *
 * @author zornx5
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class JpaUserRepositoryImpl implements UserRepository<JpaUser, Long> {

    private final JpaUserRepositoryDelegate delegate;

    @Override
    public User<JpaUser, Long> create() {
        JpaUser user = new JpaUser();
        user.init();
        return user;
    }

    @Override
    public User<JpaUser, Long> create(Long id) {
        JpaUser user = new JpaUser(id);
        user.init();
        return user;
    }

    @Override
    public User<JpaUser, Long> save(User<JpaUser, Long> user) {
        return this.delegate.save(JpaUser.of(user));
    }

    @Override
    public void delete(User<JpaUser, Long> user) {
        this.delegate.delete(JpaUser.of(user));
    }

    @Override
    public Optional<User<JpaUser, Long>> findById(Long id) {
        return CastUtils.cast(this.delegate.findById(id));
    }

    @Override
    public Optional<User<JpaUser, Long>> findByQuery(UserQuery query) {
        if (Objects.nonNull(query.getName())) {
            return CastUtils.cast(this.delegate.findByName(query.getName()));
        } else if (Objects.nonNull(query.getPhone())) {
            return CastUtils.cast(this.delegate.findByPhone(query.getPhone()));
        } else if (Objects.nonNull(query.getEmail())) {
            return CastUtils.cast(this.delegate.findByEmail(query.getEmail()));
        }
        return Optional.empty();
    }

    @Override
    public List<User<JpaUser, Long>> findAllById(Collection<Long> ids) {
        return CastUtils.cast(delegate.findAllById(ids));
    }

    @Override
    public Page<User<JpaUser, Long>> findAll(UserQuery query, Pageable pageable) {
        return CastUtils.cast(delegate.findAll(pageable));
    }
}
