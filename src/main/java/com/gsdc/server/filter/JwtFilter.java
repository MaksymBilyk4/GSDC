package com.gsdc.server.filter;

import com.gsdc.server.dto.auth.JwtAuthentication;
import com.gsdc.server.service.auth.JwtProvider;
import com.gsdc.server.utils.auth.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final String AUTHORIZATION = "Authorization";
    @Value("${jwt.authorization.user.field}")
    private String userLoginField;
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        final String token = getTokenFromRequest((HttpServletRequest) request);

        if (token != null && jwtProvider.validateAccessToken(token)) {
            final Claims claims = jwtProvider.getAccessClaims(token);
            final JwtAuthentication jwtInfoToken = JwtUtil.generate(claims, userLoginField);
            jwtInfoToken.setAuthenticated(true);
            logger.info(token);
            logger.info(jwtInfoToken.toString());
            logger.info(jwtInfoToken);
            SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest req) {
        final String bearer = req.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }

        return null;
    }

}
