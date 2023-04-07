package io.github.zornx5.infrastructure;

import io.github.zornx5.domain.entity.User;
import io.github.zornx5.domain.service.UserService;
import io.github.zornx5.infrastructure.common.exception.UserNotFoundException;
import io.github.zornx5.infrastructure.repository.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Jwt 用户详情服务
 *
 * @author zornx5
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JwtUserDetailsService<U extends User<U, PK>, PK extends Serializable> implements UserDetailsService {

    private final UserService<U, PK> userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User<U, PK> user = userService.findByQuery(UserQuery.nameOf(username)).orElseThrow(UserNotFoundException::new);
        return new JwtUserDetails<>(user);
    }
}
