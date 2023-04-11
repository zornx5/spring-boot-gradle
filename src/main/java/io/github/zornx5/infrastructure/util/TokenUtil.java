package io.github.zornx5.infrastructure.util;

import com.google.common.net.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;


/**
 * Token 工具类
 *
 * @author zornx5
 */
public class TokenUtil {

    public static final String BEARER = "Bearer";

    public static Optional<String> getAuthorizationToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.trim().startsWith(BEARER)) {
            return Optional.empty();
        }
        return Optional.of(authHeader.replace(BEARER, "").trim());
    }
}
