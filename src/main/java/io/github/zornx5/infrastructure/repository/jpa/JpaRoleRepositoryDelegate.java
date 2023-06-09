package io.github.zornx5.infrastructure.repository.jpa;

import io.github.zornx5.domain.entity.jpa.JpaRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 角色存储 Jpa 委派
 *
 * @author zornx5
 */
public interface JpaRoleRepositoryDelegate extends JpaRepository<JpaRole, Long> {
    Optional<JpaRole> findByName(String name);
}
