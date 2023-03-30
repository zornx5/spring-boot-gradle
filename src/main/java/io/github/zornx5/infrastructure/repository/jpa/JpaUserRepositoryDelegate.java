package io.github.zornx5.infrastructure.repository.jpa;

import io.github.zornx5.domain.entity.jpa.JpaUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

/**
 * 用户存储 Jpa 委派
 *
 * @author zornx5
 */
@Repository
public interface JpaUserRepositoryDelegate extends JpaRepository<JpaUser, Long> {

    Optional<JpaUser> findByName(String name);

    Optional<JpaUser> findByPhone(String phone);

    Optional<JpaUser> findByEmail(String email);

    Page<JpaUser> findByNameLikeAndPhoneAndEmailAndCreatedDateBetween(String name, String phone, String email, Date createdDateStart, Date createdDateEnd, Pageable pageable);


}
