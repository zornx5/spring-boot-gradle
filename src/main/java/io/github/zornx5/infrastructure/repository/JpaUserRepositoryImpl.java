package io.github.zornx5.infrastructure.repository;

import io.github.zornx5.domain.entity.JpaUser;
import io.github.zornx5.domain.entity.User;
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
public class JpaUserRepositoryImpl implements UserRepository<JpaUser, String> {

    private final JpaUserRepositoryDelegate delegate;

    @Override
    public User<JpaUser, String> create() {
        JpaUser user = new JpaUser();
        user.create();
        return user;
    }

    @Override
    public User<JpaUser, String> create(String id) {
        JpaUser user = new JpaUser(id);
        user.create();
        return user;
    }

    @Override
    public User<JpaUser, String> save(User<JpaUser, String> user) {
        return this.delegate.save(JpaUser.of(user));
    }

    @Override
    public void delete(User<JpaUser, String> user) {
        this.delegate.delete(JpaUser.of(user));
    }

    @Override
    public Optional<User<JpaUser, String>> findById(String id) {
        return CastUtils.cast(this.delegate.findById(id));
    }

    @Override
    public Optional<User<JpaUser, String>> findBySearch(UserSearch search) {
        if (Objects.nonNull(search.getName())) {
            return CastUtils.cast(this.delegate.findByName(search.getName()));
        } else if (Objects.nonNull(search.getPhone())) {
            return CastUtils.cast(this.delegate.findByPhone(search.getPhone()));
        } else if (Objects.nonNull(search.getEmail())) {
            return CastUtils.cast(this.delegate.findByEmail(search.getEmail()));
        }
        return Optional.empty();
    }

    @Override
    public List<User<JpaUser, String>> findAllById(Collection<String> ids) {
        return CastUtils.cast(delegate.findAllById(ids));
    }

    @Override
    public Page<User<JpaUser, String>> findAll(UserSearch search, Pageable pageable) {
        return CastUtils.cast(delegate.findAll(pageable));
    }
}
