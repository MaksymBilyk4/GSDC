package com.gsdc.server.service.auth;

import com.gsdc.server.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    // Values from application.yml - leave time for access and refresh
    @Value("${jwt.access.leave.minutes}")
    private int accessLeaveMinutes;

    @Value("${jwt.refresh.leave.days}")
    private int refreshLeaveDays;

    @Value("${jwt.authorization.user.field}")
    private String userLoginField;

    // decoded (разкодированые) access and refresh tokens
    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;

    // params of constructor are data from application.yml file secret phrases
    public JwtProvider(@Value("${jwt.secret.access}") String jwtAccessSecret,
                       @Value("${jwt.secret.refresh}") String jwtRefreshSecret) {
        this.jwtAccessSecret = getSecretKey(jwtAccessSecret);
        this.jwtRefreshSecret = getSecretKey(jwtRefreshSecret);
    }

    // decode by signature
    private SecretKey getSecretKey(String secretKey) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    // access token generating
    public String generateAccessToken (@NonNull User user) {

        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationDate = now.plusMinutes(accessLeaveMinutes).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationDate);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim(userLoginField, user.getUsername())
                .compact();
    }

    // refresh token generating
    public String generateRefreshToken (@NonNull User user) {

        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(refreshLeaveDays).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    // TOKENS VALIDATION --------------------------------
    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("Token expired", expiredJwtException);
        } catch (UnsupportedJwtException unsupportedJwtException) {
            log.error("Unsupported jwt", unsupportedJwtException);
        } catch (MalformedJwtException malformedJwtException) {
            log.error("Malformed jwt", malformedJwtException);
        } catch (SignatureException signatureException) {
            log.error("Invalid signature", signatureException);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
