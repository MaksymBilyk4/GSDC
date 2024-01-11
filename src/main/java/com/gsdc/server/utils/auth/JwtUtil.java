package com.gsdc.server.utils.auth;

import com.gsdc.server.dto.auth.JwtAuthentication;
import io.jsonwebtoken.Claims;

public class JwtUtil {

    public static JwtAuthentication generate(Claims claims, String login) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUsername(claims.get(login, String.class));
        return jwtInfoToken;
    }

}
