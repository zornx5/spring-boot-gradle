package io.github.zornx5.infrastructure.repository;

import io.github.zornx5.domain.entity.JpaRole;
import io.github.zornx5.domain.entity.JpaUser;
import io.github.zornx5.domain.entity.Role;
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
 * Jpa 角色存储实现
 *
 * @author zornx5
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class JpaRoleRepositoryImpl implements RoleRepository<JpaUser, String> {

    private final JpaRoleRepositoryDelegate delegate;

    @Override
    public Role<JpaUser, String> create(String id) {
        JpaRole role = new JpaRole(id);
        role.create();
        return role;
    }

    @Override
    public Role<JpaUser, String> save(Role<JpaUser, String> role) {
        return this.delegate.save(JpaRole.of(role));
    }

    @Override
    public void delete(Role<JpaUser, String> role) {
        this.delegate.delete(JpaRole.of(role));
    }

    @Override
    public Optional<Role<JpaUser, String>> findById(String id) {
        return CastUtils.cast(this.delegate.findById(id));
    }

    @Override
    public Optional<Role<JpaUser, String>> findBySearch(RoleSearch search) {
        if (Objects.nonNull(search.getName())) {
            return CastUtils.cast(this.delegate.findByName(search.getName()));
        }
        return Optional.empty();
    }

    @Override
    public List<Role<JpaUser, String>> findAllById(Collection<String> ids) {
        return CastUtils.cast(delegate.findAllById(ids));
    }

    @Override
    public Page<Role<JpaUser, String>> findAll(RoleSearch search, Pageable pageable) {
        return CastUtils.cast(delegate.findAll(pageable));
    }
}
