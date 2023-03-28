package io.github.zornx5.infrastructure.repository;

import io.github.zornx5.domain.entity.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户存储 Jpa 委派
 *
 * @author zornx5
 */
@Repository
public interface JpaUserRepositoryDelegate extends JpaRepository<JpaUser, String> {

    Optional<JpaUser> findByName(String name);

    Optional<JpaUser> findByPhone(String phone);

    Optional<JpaUser> findByEmail(String email);

}
