package io.github.zornx5.infrastructure.repository;

import io.github.zornx5.domain.entity.JpaResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 资源存储 Jpa 委派
 *
 * @author zornx5
 */
public interface JpaResourceRepositoryDelegate extends JpaRepository<JpaResource, String> {

    Optional<JpaResource> findByName(String name);
}
