package io.github.zornx5.domain.entity;

import io.github.zornx5.infrastructure.common.enums.UserGender;
import io.github.zornx5.infrastructure.common.enums.UserStatus;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 抽象用户
 *
 * @author zornx5
 */
@MappedSuperclass
public abstract class AbstractUser<U extends User<U, PK>, PK extends Serializable> extends AbstractExpirable<U, PK>
        implements User<U, PK> {

    @Serial
    private static final long serialVersionUID = 14130110092L;

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles().stream()
                .filter(Objects::nonNull)
                .map(Role.class::cast)
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        LocalDateTime now = LocalDateTime.now();
        return this.getExpiredDate()
                .map(expiredDate -> !now.isAfter(expiredDate))
                .orElse(true);
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getStatus().isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getStatus().isActive();
    }

    @Override
    public void init() {
        this.loginFailedAttemptsReset();
    }

    @Override
    public void loginFailedAttemptsIncrement() {
        this.setLoginFailedAttempts(this.getLoginFailedAttempts() + 1);
    }

    @Override
    public void loginFailedAttemptsReset() {
        this.setLoginFailedAttempts(0);
    }

    @Override
    public Builder<U, PK> toBuilder() {
        return new AbstractBuilder<>(this) {
        };
    }

    protected abstract static class AbstractBuilder<U extends User<U, PK>, PK extends Serializable> implements Builder<U, PK> {

        private final AbstractUser<U, PK> user;

        public AbstractBuilder(AbstractUser<U, PK> user) {
            this.user = user;
        }

        @Override
        public User<U, PK> build() {
            return this.user;
        }

        @Override
        public Builder<U, PK> id(PK id) {
            this.user.setId(id);
            return this;
        }

        @Override
        public Builder<U, PK> name(String name) {
            this.user.setName(name);
            return this;
        }

        @Override
        public Builder<U, PK> description(String description) {
            this.user.setDescription(description);
            return this;
        }

        @Override
        public Builder<U, PK> firstName(String firstName) {
            this.user.setFirstName(firstName);
            return this;
        }

        @Override
        public Builder<U, PK> lastName(String lastName) {
            this.user.setLastName(lastName);
            return this;
        }

        @Override
        public Builder<U, PK> avatar(String avatar) {
            this.user.setAvatar(avatar);
            return this;
        }

        @Override
        public Builder<U, PK> age(int age) {
            this.user.setAge(age);
            return this;
        }

        @Override
        public Builder<U, PK> gender(UserGender gender) {
            this.user.setGender(gender);
            return this;
        }

        @Override
        public Builder<U, PK> email(String email) {
            this.user.setEmail(email);
            return this;
        }

        @Override
        public Builder<U, PK> phone(String phone) {
            this.user.setPhone(phone);
            return this;
        }

        @Override
        public Builder<U, PK> address(String address) {
            this.user.setAddress(address);
            return this;
        }

        @Override
        public Builder<U, PK> password(String password) {
            this.user.setPassword(password);
            return this;
        }

        @Override
        public Builder<U, PK> status(UserStatus status) {
            this.user.setStatus(status);
            return this;
        }

        @Override
        public Builder<U, PK> roles(Collection<Role<U, PK>> roles) {
            this.user.setRoles(roles);
            return this;
        }

        @Override
        public Builder<U, PK> expiredTime(LocalDateTime expiredTime) {
            this.user.setExpiredDate(expiredTime);
            return this;
        }
    }
}
