package io.github.zornx5.infrastructure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.issuer:ZornX5}")
    private String issuer;

    @Value("${application.security.jwt.secret-key:404E635266556A586E327235753T782F413F4428472B4B6250645367566B5970}")
    private String secretKey;

    @Value("${application.security.jwt.expiration:5}")
    @DurationUnit(ChronoUnit.MINUTES)
    private Duration expiration;

    @Value("${application.security.jwt.refresh-token.expiration:30}")
    @DurationUnit(ChronoUnit.MINUTES)
    private Duration refreshExpiration;

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, expiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, Duration expiration) {
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expirationAt = issuedAt.plus(expiration);

        return Jwts
                .builder()
                .setHeaderParam("type", "jwt")
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuer(issuer)
                .setIssuedAt(Date.from(issuedAt.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expirationAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String subject = extractSubject(token);
        return (subject.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
