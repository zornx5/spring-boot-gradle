package io.github.zornx5.infrastructure;

import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.domain.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class JwtUserDetails<U extends User<U, PK>, PK extends Serializable> implements UserDetails {

    private final User<U, PK> user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .filter(Role.class::isInstance)
                .map(Role.class::cast)
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        LocalDateTime now = LocalDateTime.now();
        return user.getExpiredDate()
                .map(expiredDate -> !now.isBefore(expiredDate))
                .orElse(true);
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getStatus().isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus().isActive();
    }
}
