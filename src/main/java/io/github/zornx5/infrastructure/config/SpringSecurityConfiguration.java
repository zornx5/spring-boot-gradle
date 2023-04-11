package io.github.zornx5.infrastructure.config;

import io.github.zornx5.infrastructure.JwtService;
import io.github.zornx5.infrastructure.filter.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;


/**
 * Spring Security 设置
 *
 * @author zornx5
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class SpringSecurityConfiguration {

    /**
     * API 接口白名单
     */
    public static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/webjars/swagger-ui/**"
    };
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    private static void onLogoutSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        log.info("Success logout");
        SecurityContextHolder.clearContext();
    }

    private static void accessDeniedHandler(HttpServletRequest request,
                                            HttpServletResponse response,
                                            AccessDeniedException accessDeniedException) throws IOException {
        log.error("Sorry you don not enough permissions to access it", accessDeniedException);
        response.sendError(HttpStatus.FORBIDDEN.value(), accessDeniedException.getMessage());
    }

    private static void authenticationEntryPoint(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 AuthenticationException authenticationException) throws IOException {
        log.error("This token is null or token is invalid", authenticationException);
        response.sendError(HttpStatus.UNAUTHORIZED.value(), authenticationException.getMessage());
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // formatter:off
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.GET, SWAGGER_WHITELIST).permitAll()
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/users").permitAll()
                .anyRequest().authenticated()
                // 禁用 Session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(
                        authenticationManager(),
                        SpringSecurityConfiguration::authenticationEntryPoint,
                        userDetailsService,
                        jwtService), UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessHandler(SpringSecurityConfiguration::onLogoutSuccess)
                .and().exceptionHandling()
                .accessDeniedHandler(SpringSecurityConfiguration::accessDeniedHandler)
                .authenticationEntryPoint(SpringSecurityConfiguration::authenticationEntryPoint)
        ;
        // @formatter:on

        return http.build();
    }
}
