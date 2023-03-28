package io.github.zornx5.domain.entity;

import io.github.zornx5.infrastructure.common.DomainBuilder;
import io.github.zornx5.infrastructure.common.enums.UserGender;
import io.github.zornx5.infrastructure.common.enums.UserStatus;
import org.springframework.data.domain.Auditable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 用户接口
 *
 * @author zornx5
 */
public interface User<U, PK extends Serializable> extends Expirable<PK, LocalDateTime>,
        Auditable<U, PK, LocalDateTime>, Nameable<PK>, DomainBuilder.ToBuilder<User.Builder<U, PK>> {

    /**
     * 创建对象初始化内容
     */
    void create();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getAvatar();

    void setAvatar(String avatar);

    int getAge();

    void setAge(int age);

    UserGender getGender();

    void setGender(UserGender gender);

    String getEmail();

    void setEmail(String email);

    String getPhone();

    void setPhone(String phone);

    String getAddress();

    void setAddress(String address);

    String getPassword();

    void setPassword(String password);

    int getLoginFailedAttempts();

    void setLoginFailedAttempts(int loginFailedAttempts);

    UserStatus getStatus();

    void setStatus(UserStatus status);

    Collection<Role<U, PK>> getRoles();

    void setRoles(Collection<Role<U, PK>> roles);

    interface Builder<U, PK extends Serializable> extends DomainBuilder<User<U, PK>> {
        Builder<U, PK> id(PK id);

        Builder<U, PK> name(String name);

        Builder<U, PK> description(String description);

        Builder<U, PK> firstName(String firstName);

        Builder<U, PK> lastName(String lastName);

        Builder<U, PK> avatar(String avatar);

        Builder<U, PK> age(int age);

        Builder<U, PK> gender(UserGender gender);

        Builder<U, PK> email(String email);

        Builder<U, PK> phone(String phone);

        Builder<U, PK> address(String address);

        Builder<U, PK> password(String password);

        Builder<U, PK> status(UserStatus status);

        Builder<U, PK> expiredTime(LocalDateTime expiredTime);

        Builder<U, PK> roles(Collection<Role<U, PK>> roles);
    }
}
