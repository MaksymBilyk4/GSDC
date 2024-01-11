package com.gsdc.server.service.auth;

import com.gsdc.server.dto.auth.*;
import com.gsdc.server.entity.RefreshTokenStore;
import com.gsdc.server.entity.User;
import com.gsdc.server.exceptions.WrongPasswordException;
import com.gsdc.server.repository.RefreshTokenStoreRepository;
import com.gsdc.server.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JwtAuthService implements AuthService {

    private final UserService userService;
    private final RefreshTokenStoreRepository refreshTokenStoreRepository;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public AccountCheckResponse account(@NonNull AccountCheckRequest req) {
        try {
            userService.findUserByUsernameAndThrowException(req.getLogin());
        } catch (Exception e) {
            userService.findUserByEmailAndThrowException(req.getLogin());
        }

        return new AccountCheckResponse(req.getLogin());
    }

    @Override
    public JwtResponse login(@NonNull JwtRequest req) {
        return login(req.getLogin(), req.getPassword());
    }

    public JwtResponse login(String login, String password) {
        User user;

        try {
            user = userService.findUserByUsernameAndThrowException(login);
        } catch (Exception e) {
            user = userService.findUserByEmailAndThrowException(login);
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            return getJwtResponse(user);
        }

        throw new WrongPasswordException();
    }

    public JwtResponse getJwtResponse(User user) {
        final String newAccessToken = jwtProvider.generateAccessToken(user);
        final String newRefreshToken = jwtProvider.generateRefreshToken(user);

        RefreshTokenStore refreshTokenStore = new RefreshTokenStore(user.getUsername(), newRefreshToken);
        refreshTokenStoreRepository.save(refreshTokenStore);

        return new JwtResponse(newAccessToken, newRefreshToken);
    }

    @Override
    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            Optional<RefreshTokenStore> refreshTokenStore = refreshTokenStoreRepository.findFirstByLoginOrderByIdDesc(login);

            if (refreshTokenStore.isPresent()) {
                String saveRefreshToken = refreshTokenStore.get().getRefreshToken();

                if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                    final User user = userService.findUserByUsernameAndThrowException(login);
                    final String accessToken = jwtProvider.generateAccessToken(user);

                    return new JwtResponse(accessToken, refreshToken);
                }
            }
        }

        return new JwtResponse(null, null);
    }

    @Override
    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            System.out.println("refresh-here");
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            Optional<RefreshTokenStore> refreshTokenStore = refreshTokenStoreRepository.findFirstByLoginOrderByIdDesc(login);

            if (refreshTokenStore.isPresent()) {
                String saveRefreshToken = refreshTokenStore.get().getRefreshToken();

                if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                    User user = userService.findUserByUsernameAndThrowException(login);
                    return getJwtResponse(user);
                }
            }
        }

        throw new RuntimeException();
    }

    @Override
    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    @Transactional
    public void deleteAllByLogin(String login) {
        refreshTokenStoreRepository.deleteAllByLogin(login);
    }
}
