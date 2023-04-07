package io.github.zornx5.infrastructure;

import io.github.zornx5.domain.entity.Role;
import io.github.zornx5.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@RequiredArgsConstructor
public class JwtUserDetails<U extends User<U, PK>, PK extends Serializable> implements UserDetails {

    private final User<U, PK> user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        if (!Objects.isNull(user.getRoles())) {
            for (Object object : user.getRoles()) {
                if (object instanceof Role<?, ?> role) {
                    simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
                }
            }
        }
        return simpleGrantedAuthorities;
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
        return !now.isBefore(user.getExpiredDate().orElse(now));
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
