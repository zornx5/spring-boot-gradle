package io.github.zornx5.infrastructure.repository.jpa;

import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.domain.entity.jpa.JpaRole;
import io.github.zornx5.domain.entity.jpa.JpaUser;
import io.github.zornx5.infrastructure.repository.RoleQuery;
import io.github.zornx5.infrastructure.repository.RoleRepository;
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
public class JpaRoleRepositoryImpl implements RoleRepository<JpaUser, Long> {

    private final JpaRoleRepositoryDelegate delegate;

    @Override
    public Role<JpaUser, Long> create() {
        JpaRole role = new JpaRole();
        role.init();
        return role;
    }

    @Override
    public Role<JpaUser, Long> create(Long id) {
        JpaRole role = new JpaRole(id);
        role.init();
        return role;
    }

    @Override
    public Role<JpaUser, Long> save(Role<JpaUser, Long> role) {
        return this.delegate.save(JpaRole.of(role));
    }

    @Override
    public void delete(Role<JpaUser, Long> role) {
        this.delegate.delete(JpaRole.of(role));
    }

    @Override
    public Optional<Role<JpaUser, Long>> findById(Long id) {
        return CastUtils.cast(this.delegate.findById(id));
    }

    @Override
    public Optional<Role<JpaUser, Long>> findByQuery(RoleQuery query) {
        if (Objects.nonNull(query.getName())) {
            return CastUtils.cast(this.delegate.findByName(query.getName()));
        }
        return Optional.empty();
    }

    @Override
    public List<Role<JpaUser, Long>> findAllById(Collection<Long> ids) {
        return CastUtils.cast(delegate.findAllById(ids));
    }

    @Override
    public Page<Role<JpaUser, Long>> findAll(RoleQuery query, Pageable pageable) {
        return CastUtils.cast(delegate.findAll(pageable));
    }
}
