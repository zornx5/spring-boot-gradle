package io.github.zornx5.infrastructure.filter;

import io.github.zornx5.infrastructure.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

import static io.github.zornx5.infrastructure.util.TokenUtil.getAuthorizationToken;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   AuthenticationEntryPoint authenticationEntryPoint,
                                   UserDetailsService userDetailsService,
                                   JwtService jwtService) {
        super(authenticationManager, authenticationEntryPoint);
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        getAuthorizationToken(request).ifPresent(token -> {
            final String subject = jwtService.extractSubject(token);
            if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(subject);
                if (jwtService.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            token,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        });
        filterChain.doFilter(request, response);
    }
}
