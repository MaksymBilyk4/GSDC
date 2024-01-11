package com.gsdc.server.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JwtRequest {

    private String login;

    private String password;

}